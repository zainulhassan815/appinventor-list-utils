# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.dreamers.listutils.ListUtils {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/dreamers/listutils/repack'
-flattenpackagehierarchy
-dontpreverify
