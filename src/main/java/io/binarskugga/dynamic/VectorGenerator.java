package io.binarskugga.dynamic;

import com.squareup.javapoet.*;
import lombok.*;
import org.jetbrains.annotations.Contract;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

final class VectorGenerator {
	public static void main(String[] args) throws IOException {
		TypeName[] types = {TypeName.FLOAT, TypeName.INT, TypeName.DOUBLE, TypeName.LONG};
		int[] dimensions = {2, 3, 4};
		String packageName = "io.binarskugga.math";

		for (TypeName typeName : types) {
			String type = typeName.toString();
			String suffix = type.charAt(0) + "";
			boolean isFloatOrDouble = type.equals("float") || type.equals("double");

			System.out.println(packageName + "." + typeName + "." + suffix + "." + isFloatOrDouble);

			for (int dimension : dimensions) {
				String className = "Vec" + dimension + suffix;
				String fieldsStr = "x, y";
				if (dimension == 3) fieldsStr += ", z";
				if (dimension == 4) fieldsStr += ", z, w";
				String[] fields = fieldsStr.split(", ");

				ClassName fullClass = ClassName.get(packageName, className);
				ClassName fullInterface = ClassName.get(packageName, "IVector");
				ParameterSpec notNullOther = ParameterSpec.builder(fullClass, "other")
						.addAnnotation(NonNull.class).build();
				AnnotationSpec pure = AnnotationSpec.builder(Contract.class)
						.addMember("pure", "$L", true).build();
				String nonIntCast = typeName == TypeName.FLOAT || typeName == TypeName.DOUBLE ? "" : "(" + typeName + ") ";
				TypeName scalarReturn = typeName == TypeName.DOUBLE || typeName == TypeName.LONG
						? TypeName.DOUBLE : TypeName.FLOAT;
				String floatConvert = scalarReturn == TypeName.FLOAT ? "(float) " : "";

				TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
						.addModifiers(Modifier.PUBLIC, Modifier.FINAL)
						.addSuperinterface(ParameterizedTypeName.get(
								fullInterface,
								fullClass
						))
						.addAnnotation(Getter.class)
						.addAnnotation(Setter.class)
						.addAnnotation(NoArgsConstructor.class)
						.addAnnotation(AllArgsConstructor.class);

				// Fields
				for (String field : fields) {
					classBuilder.addField(typeName, field, Modifier.PUBLIC);
				}

				// Fields as params
				List<ParameterSpec> parameterSpecs = Arrays.stream(fields)
						.map(field -> ParameterSpec.builder(typeName, field).build())
						.collect(Collectors.toList());
				ParameterSpec singleParam = ParameterSpec.builder(typeName, "w").build();

				// Constructor
				MethodSpec constructor = MethodSpec.constructorBuilder()
						.addModifiers(Modifier.PUBLIC)
						.addParameter(singleParam)
						.addCode(Arrays.stream(fields)
								.map(f -> "this." + f + " = " + singleParam.name + ";")
								.collect(Collectors.joining("\n")))
						.build();
				classBuilder.addMethod(constructor);

				// Set (deconstructed)
				MethodSpec setDec = MethodSpec.methodBuilder("set")
						.addModifiers(Modifier.PUBLIC)
						.returns(fullClass)
						.addParameters(parameterSpecs)
						.addCode(
								Arrays.stream(fields)
										.map(f -> "this." + f + " = " + f + ";")
										.collect(Collectors.joining("\n"))
						)
						.addCode("\nreturn this;")
						.build();
				classBuilder.addMethod(setDec);

				// Set (single)
				MethodSpec setSingle = MethodSpec.methodBuilder("set")
						.addModifiers(Modifier.PUBLIC)
						.returns(fullClass)
						.addParameter(singleParam)
						.addCode(
								Arrays.stream(fields)
										.map(f -> "this." + f + " = " + singleParam.name + ";")
										.collect(Collectors.joining("\n"))
						)
						.addCode("\nreturn this;")
						.build();
				classBuilder.addMethod(setSingle);

				// Set (other)
				MethodSpec setOther = MethodSpec.methodBuilder("set")
						.addModifiers(Modifier.PUBLIC)
						.returns(fullClass)
						.addParameter(notNullOther)
						.addCode(
								Arrays.stream(fields)
										.map(f -> "this." + f + " = other." + f + ";")
										.collect(Collectors.joining("\n"))
						)
						.addCode("\nreturn this;")
						.build();
				classBuilder.addMethod(setOther);

				// toArray
				MethodSpec toArray = MethodSpec.methodBuilder("toArray")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure)
						.returns(ArrayTypeName.of(typeName))
						.addCode("return new " + typeName + "[]{" +
								Arrays.stream(fields)
										.map(f -> "this." + f)
										.collect(Collectors.joining(", ")) +
								"};", ArrayTypeName.of(typeName))
						.build();
				classBuilder.addMethod(toArray);

				// toString
				MethodSpec toString = MethodSpec.methodBuilder("toString")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure)
						.returns(String.class)
						.addCode("return \"[\" + " +
								Arrays.stream(fields)
										.map(f -> "this." + f)
										.collect(Collectors.joining(" + \", \" + "))
								+ " + \"]\";")
						.build();
				classBuilder.addMethod(toString);

				// Zero
				MethodSpec zero = MethodSpec.methodBuilder("zero")
						.addModifiers(Modifier.PUBLIC)
						.returns(fullClass)
						.addCode(
								Arrays.stream(fields)
										.map(f -> "this." + f + " = 0;")
										.collect(Collectors.joining("\n"))
						)
						.addCode("\nreturn this;")
						.build();
				classBuilder.addMethod(zero);

				// Negate
				MethodSpec negate = MethodSpec.methodBuilder("negate")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addCode("return this.mul(-1);", fullClass)
						.build();
				classBuilder.addMethod(negate);

				// Add (deconstructed)
				MethodSpec addDec = MethodSpec.methodBuilder("add")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameters(parameterSpecs)
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> "this." + f + " + " + f)
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(addDec);

				// Add (single)
				MethodSpec addSingle = MethodSpec.methodBuilder("add")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameter(singleParam)
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> "this." + f + " + " + singleParam.name)
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(addSingle);

				// Add (other)
				MethodSpec addOther = MethodSpec.methodBuilder("add")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameter(notNullOther)
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> f + " + other." + f)
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(addOther);

				// Sub (deconstructed)
				MethodSpec subDec = MethodSpec.methodBuilder("sub")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameters(parameterSpecs)
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> "this." + f + " - " + f)
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(subDec);

				// Sub (single)
				MethodSpec subSingle = MethodSpec.methodBuilder("sub")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameter(singleParam)
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> "this." + f + " - " + singleParam.name)
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(subSingle);

				// Sub (other)
				MethodSpec subOther = MethodSpec.methodBuilder("sub")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameter(notNullOther)
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> f + " - other." + f)
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(subOther);

				// Mul (deconstructed)
				MethodSpec mulDec = MethodSpec.methodBuilder("mul")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameters(parameterSpecs)
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> "this." + f + " * " + f)
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(mulDec);

				// Mul (scalar)
				MethodSpec mul = MethodSpec.methodBuilder("mul")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameter(TypeName.FLOAT, "scalar")
						.addCode(isFloatOrDouble ?
								"return new $T(" +
										Arrays.stream(fields)
												.map(f -> f + " * scalar")
												.collect(Collectors.joining(", ")) +
										");" :
								"return new $T(" +
										Arrays.stream(fields)
												.map(f -> "(" + type + ")(" + f + " * scalar)")
												.collect(Collectors.joining(", ")) +
										");", fullClass)
						.build();
				classBuilder.addMethod(mul);

				// Mul (other)
				MethodSpec mulOther = MethodSpec.methodBuilder("mul")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameter(notNullOther)
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> f + " * other." + f)
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(mulOther);

				// Div (deconstructed)
				MethodSpec divDec = MethodSpec.methodBuilder("div")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameters(parameterSpecs)
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> "this." + f + " / " + f)
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(divDec);

				// Div (scalar)
				MethodSpec div = MethodSpec.methodBuilder("div")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameter(TypeName.FLOAT, "scalar")
						.addCode(isFloatOrDouble ?
								"return new $T(" +
										Arrays.stream(fields)
												.map(f -> f + " / scalar")
												.collect(Collectors.joining(", ")) +
										");" :
								"return new $T(" +
										Arrays.stream(fields)
												.map(f -> "(" + type + ")(" + f + " / scalar)")
												.collect(Collectors.joining(", ")) +
										");", fullClass)
						.build();
				classBuilder.addMethod(div);

				// Div (other)
				MethodSpec divOther = MethodSpec.methodBuilder("div")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameter(notNullOther)
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> f + " / other." + f)
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(divOther);

				// Dot
				MethodSpec dot = MethodSpec.methodBuilder("dot")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure)
						.returns(typeName)
						.addParameter(notNullOther)
						.addCode("return " +
								Arrays.stream(fields)
										.map(f -> f + " * other." + f)
										.collect(Collectors.joining(" + ")) +
								";")
						.build();
				classBuilder.addMethod(dot);

				// Length
				MethodSpec length = MethodSpec.methodBuilder("length")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure)
						.returns(scalarReturn)
						.addParameter(TypeName.BOOLEAN, "squared")
						.addCode("if(squared) return " +
								Arrays.stream(fields)
										.map(f -> f + " * " + f)
										.collect(Collectors.joining(" + ")) +
								";"
						)
						.addCode("\nelse return sqrt(" +
								Arrays.stream(fields)
										.map(f -> f + " * " + f)
										.collect(Collectors.joining(" + ")) +
								");")
						.build();
				classBuilder.addMethod(length);

				MethodSpec lengthDefault = MethodSpec.methodBuilder("length")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure)
						.returns(scalarReturn)
						.addCode("return this.length(false);")
						.build();
				classBuilder.addMethod(lengthDefault);

				// Distance (deconstructed)
				MethodSpec distanceDec = MethodSpec.methodBuilder("distance")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure)
						.returns(scalarReturn)
						.addParameters(parameterSpecs)
						.addParameter(TypeName.BOOLEAN, "squared")
						.addCode("double squaredDistance = " +
								Arrays.stream(fields)
										.map(f -> "((this." + f + " - " + f + ") * (this." + f + " - " + f + "))")
										.collect(Collectors.joining(" + ")) +
								";\n")
						.addCode("if(!squared) return " + floatConvert + "sqrt(squaredDistance);\n")
						.addCode("return " + floatConvert + "squaredDistance;\n")
						.build();
				classBuilder.addMethod(distanceDec);

				MethodSpec distanceDecDefault = MethodSpec.methodBuilder("distance")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure)
						.returns(scalarReturn)
						.addParameters(parameterSpecs)
						.addCode("return this.distance(" +
								String.join(", ", fields)
								+ ", false);")
						.build();
				classBuilder.addMethod(distanceDecDefault);

				// Distance (other)
				MethodSpec distanceOther = MethodSpec.methodBuilder("distance")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure)
						.returns(scalarReturn)
						.addParameter(notNullOther)
						.addParameter(TypeName.BOOLEAN, "squared")
						.addCode("double squaredDistance = " +
								Arrays.stream(fields)
										.map(f -> "((this." + f + " - other." + f + ") * (this." + f + " - other." + f + "))")
										.collect(Collectors.joining(" + ")) +
								";\n")
						.addCode("if(!squared) return " + floatConvert + "sqrt(squaredDistance);\n")
						.addCode("return " + floatConvert + "squaredDistance;\n")
						.build();
				classBuilder.addMethod(distanceOther);

				MethodSpec distanceOtherDefault = MethodSpec.methodBuilder("distance")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure)
						.returns(scalarReturn)
						.addParameter(notNullOther)
						.addCode("return this.distance(other, false);")
						.build();
				classBuilder.addMethod(distanceOtherDefault);

				// Normalize
				MethodSpec normalize = MethodSpec.methodBuilder("normalize")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(NonNull.class)
						.addAnnotation(pure)
						.returns(fullClass)
						.addCode(scalarReturn + " vlength = this.length();\n")
						.addCode("return new $T(" + Arrays.stream(fields)
								.map(f -> nonIntCast + "(this." + f + " / vlength)")
								.collect(Collectors.joining(", "))
								+ ");", fullClass
						)
						.build();
				classBuilder.addMethod(normalize);

				if (dimension == 3) {
					// Cross (other)
					MethodSpec cross = MethodSpec.methodBuilder("cross")
							.addModifiers(Modifier.PUBLIC)
							.addAnnotation(pure)
							.returns(fullClass)
							.addParameter(notNullOther)
							.addCode("""
									return new $T(
										this.y * other.z - this.z * other.y,
										this.z * other.x - this.x * other.z,
										this.x * other.y - this.y * other.x
									);
								""", fullClass)
							.build();
					classBuilder.addMethod(cross);
				}

				// Lerp (other)
				MethodSpec lerpOther = MethodSpec.methodBuilder("lerp")
						.addModifiers(Modifier.PUBLIC)
						.addAnnotation(pure).addAnnotation(NonNull.class)
						.returns(fullClass)
						.addParameter(notNullOther)
						.addParameter(TypeName.FLOAT, "t")
						.addCode("return new $T(" +
								Arrays.stream(fields)
										.map(f -> nonIntCast + "(this." + f + " + (other." + f + " - this." + f + ") * t)")
										.collect(Collectors.joining(", ")) +
								");", fullClass)
						.build();
				classBuilder.addMethod(lerpOther);

				// Write to file
				ClassName StaticMath = ClassName.get("io.binarskugga.math", "StaticMath");
				JavaFile javaFile = JavaFile
						.builder(packageName, classBuilder.build())
						.addStaticImport(StaticMath, "sqrt")
						.build();

				try {
					javaFile.writeTo(Paths.get("src/main/java"));
				} catch (IOException e) {
				}
			}
		}
	}

	public static String injectImports(@NonNull JavaFile javaFile, String... imports) {
		String rawSource = javaFile.toString();

		List<String> result = new ArrayList<>();
		for (String s : rawSource.split("\n", -1)) {
			result.add(s);
			if (s.startsWith("package ")) {
				result.add("");
				for (String i : imports) {
					result.add("import " + i + ";");
				}
			}
		}
		return String.join("\n", result);
	}
}
