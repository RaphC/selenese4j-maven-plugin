package org.rcr.maven.selenese4j.transform;

/**
 * 
 * @author Raphael
 *
 */
class ClassBean {

  private String packageName;
  private String className;
  private String methodBody;
  private String loopCount;
  private String cuncurrentUsers;

  public String getLoopCount() {
    return loopCount;
  }

  public void setLoopCount(String loopCount) {
    this.loopCount = loopCount;
  }

  public String getCuncurrentUsers() {
    return cuncurrentUsers;
  }

  public void setCuncurrentUsers(String cuncurrentUsers) {
    this.cuncurrentUsers = cuncurrentUsers;
  }

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
