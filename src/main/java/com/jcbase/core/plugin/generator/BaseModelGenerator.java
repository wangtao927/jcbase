/**
 * Copyright (c) 2011-2016, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jcbase.core.plugin.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.jfinal.kit.StrKit;

/**
 * Base model 生成器
 */
public class BaseModelGenerator {
	
	protected String packageTemplate =
		"package %s;%n%n";
	protected String importTemplate =
		"import com.jcbase.core.model.BaseModel;%n" +
		"import com.jfinal.plugin.activerecord.IBean;%n%n";
	protected String classDefineTemplate =
		"/**%n" +
		" * Generated by JFinal, do not modify this file.%n" +
		" */%n" +
		"@SuppressWarnings(\"serial\")%n" +
		"public abstract class %s<M extends %s<M>> extends BaseModel<M> implements IBean {%n%n";
	protected String setterTemplate =
			"\tpublic void %s(%s %s) {%n" +
				"\t\tset(\"%s\", %s);%n" +
			"\t}%n%n";
	protected String getterTemplate =
			"\tpublic %s %s() {%n" +
				"\t\treturn get(\"%s\");%n" +
			"\t}%n%n";
	
	protected String baseModelPackageName;
	protected String baseModelOutputDir;
	
	protected JavaKeyword javaKeyword = new JavaKeyword();
	
	public BaseModelGenerator(String baseModelPackageName, String baseModelOutputDir) {
		if (StrKit.isBlank(baseModelPackageName))
			throw new IllegalArgumentException("baseModelPackageName can not be blank.");
		if (baseModelPackageName.contains("/") || baseModelPackageName.contains("\\"))
			throw new IllegalArgumentException("baseModelPackageName error : " + baseModelPackageName);
		if (StrKit.isBlank(baseModelOutputDir))
			throw new IllegalArgumentException("baseModelOutputDir can not be blank.");
		
		this.baseModelPackageName = baseModelPackageName;
		this.baseModelOutputDir = baseModelOutputDir;
	}
	
	public void generate(List<TableMeta> tableMetas) {
		System.out.println("Generate base model ...");
		for (TableMeta tableMeta : tableMetas)
			genBaseModelContent(tableMeta);
		wirtToFile(tableMetas);
	}
	
	protected void genBaseModelContent(TableMeta tableMeta) {
		StringBuilder ret = new StringBuilder();
		genPackage(ret);
		genImport(ret);
		genClassDefine(tableMeta, ret);
		for (ColumnMeta columnMeta : tableMeta.columnMetas) {
			genSetMethodName(columnMeta, ret);
			genGetMethodName(columnMeta, ret);
		}
		ret.append(String.format("}%n"));
		tableMeta.baseModelContent = ret.toString();
	}
	
	protected void genPackage(StringBuilder ret) {
		ret.append(String.format(packageTemplate, baseModelPackageName));
	}
	
	protected void genImport(StringBuilder ret) {
		ret.append(String.format(importTemplate));
	}
	
	protected void genClassDefine(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(classDefineTemplate, tableMeta.baseModelName, tableMeta.baseModelName));
	}
	
	protected void genSetMethodName(ColumnMeta columnMeta, StringBuilder ret) {
		String setterMethodName = "set" + StrKit.firstCharToUpperCase(columnMeta.attrName);
		// 如果 setter 参数名为 java 语言关键字，则添加下划线前缀 "_"
		String argName = javaKeyword.contains(columnMeta.attrName) ? "_" + columnMeta.attrName : columnMeta.attrName;
		String setter = String.format(setterTemplate, setterMethodName, columnMeta.javaType, argName, columnMeta.name, argName);
		ret.append(setter);
	}
	
	protected void genGetMethodName(ColumnMeta columnMeta, StringBuilder ret) {
		String getterMethodName = "get" + StrKit.firstCharToUpperCase(columnMeta.attrName);
		String getter = String.format(getterTemplate, columnMeta.javaType, getterMethodName, columnMeta.name);
		ret.append(getter);
	}
	
	protected void wirtToFile(List<TableMeta> tableMetas) {
		try {
			for (TableMeta tableMeta : tableMetas)
				wirtToFile(tableMeta);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * base model 覆盖写入
	 */
	protected void wirtToFile(TableMeta tableMeta) throws IOException {
		File dir = new File(baseModelOutputDir);
		if (!dir.exists())
			dir.mkdirs();
		
		String target = baseModelOutputDir + File.separator + tableMeta.baseModelName + ".java";
		FileWriter fw = new FileWriter(target);
		try {
			fw.write(tableMeta.baseModelContent);
		}
		finally {
			fw.close();
		}
	}
}






