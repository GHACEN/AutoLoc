package tn.esprit.autoloce1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String numPermis;

}
