package module;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
public @Retention(RetentionPolicy.RUNTIME) @interface mod {
    String name()     default ("");
    String desc()     default ("");
    String type()     default ("");
    String cmds()     default ("");
    String topics()   default ("");
}
