package web.projet.fournisseurIdentite.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;
import web.projet.fournisseurIdentite.models.Sexe;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T19:48:19+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.5 (Ubuntu)"
)
@Component
public class SexeMapperImpl implements SexeMapper {

    @Override
    public Sexe toSexe(SexeDTO sexeDTO) {
        if ( sexeDTO == null ) {
            return null;
        }

        Sexe sexe = new Sexe();

        if ( sexeDTO.getId() != null ) {
            sexe.setId( sexeDTO.getId().longValue() );
        }
        sexe.setSexe( sexeDTO.getSexe() );

        return sexe;
    }

    @Override
    public SexeDTO toSexeDTO(Sexe sexe) {
        if ( sexe == null ) {
            return null;
        }

        SexeDTO sexeDTO = new SexeDTO();

        if ( sexe.getId() != null ) {
            sexeDTO.setId( sexe.getId().intValue() );
        }
        sexeDTO.setSexe( sexe.getSexe() );

        return sexeDTO;
    }
}
