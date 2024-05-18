package cos.dstruct.klassroom;

import java.lang.annotation.*;

// Credit to https://www.baeldung.com/gson-exclude-fields-serialization section 5.3 for this solution
// I couldn't find another way to implement it so it
// is pretty copy and paste (don't worry, I did type it out myself)

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Exclude {
}
