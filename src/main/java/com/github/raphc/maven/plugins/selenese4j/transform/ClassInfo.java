package com.github.raphc.maven.plugins.selenese4j.transform;

/**
 * 
 * @author Raphael
 * 
 */
class ClassInfo {

  private String packageName;
  private String className;
  private String methodBody;

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getMethodBody() {
    return methodBody;
  }

  public void setMethodBody(String methodBody) {
    this.methodBody = methodBody;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  @Override
  public String toString() {
    return "ClassBean{" + packageName + "." + className + "" + ", methodBody=" + methodBody + "}";
  }

}
