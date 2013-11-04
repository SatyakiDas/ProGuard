#
# This ProGuard configuration file illustrates how to process applications.
#

# Specify the library jars, input jars, and output jar.

-libraryjars <java.home>/lib/rt.jar
-injars      in.jar
-outjar      out.jar

# Preserve all public applications, and print out which ones.

-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

-printseeds

# Some jars may contain more items that need to be preserved, e.g.:
# -keep public class mypackage.MyClass
# -keep public interface mypackage.MyInterface
# -keep public class * implements mypackage.MyInterface
