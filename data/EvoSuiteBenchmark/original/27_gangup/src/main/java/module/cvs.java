package module;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
public @Retention(RetentionPolicy.RUNTIME) @interface cvs {
    String file()     default ("");
    String revision() default ("");
    String date()     default ("");
    String author()   default ("");
    String tag()      default ("");
    String build()    default ("");
}
