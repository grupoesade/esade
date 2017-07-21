/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Paulino Francisco
 */
@Embeddable
public class UsergrupoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_grupo", nullable = false, length = 2147483647)
    private String idGrupo;
    @Basic(optional = false)
    @Column(name = "utilizador", nullable = false, length = 2147483647)
    private String utilizador;

    public UsergrupoPK() {
    }

    public UsergrupoPK(String idGrupo, String utilizador) {
        this.idGrupo = idGrupo;
        this.utilizador = utilizador;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        hash += (utilizador != null ? utilizador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsergrupoPK)) {
            return false;
        }
        UsergrupoPK other = (UsergrupoPK) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        if ((this.utilizador == null && other.utilizador != null) || (this.utilizador != null && !this.utilizador.equals(other.utilizador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.UsergrupoPK[ idGrupo=" + idGrupo + ", utilizador=" + utilizador + " ]";
    }
    
}
