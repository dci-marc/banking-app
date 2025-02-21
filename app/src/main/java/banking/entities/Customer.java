package banking.entities;

import lombok.*;
import banking.interfaces.entities.EntityInterface;

@Getter @Setter
public final class Customer implements EntityInterface {
    @NonNull
    private String id;
    @NonNull
    private String username;
    @NonNull
    private String password = "";
    @NonNull
    private String firstName;
    @NonNull
    private Boolean active = true;

    @Override
    public String toString() {
        return String.join(",",
                this.getId(),
                this.getUsername(),
                this.getPassword(),
                this.getFirstName(),
                this.getActive().toString()
        );
    }
}
