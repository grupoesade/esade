/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Paulino Francisco
 */
@Entity
@Table(name = "categoriaa", catalog = "integra", schema = "esade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoriaa.findAll", query = "SELECT c FROM Categoriaa c"),
    @NamedQuery(name = "Categoriaa.findByIdcategoria", query = "SELECT c FROM Categoriaa c WHERE c.idcategoria = :idcategoria"),
    @NamedQuery(name = "Categoriaa.findByDescricao", query = "SELECT c FROM Categoriaa c WHERE c.descricao = :descricao")})
public class Categoriaa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcategoria", nullable = false)
    private Short idcategoria;
    @Column(name = "descricao", length = 255)
    private String descricao;
    @OneToMany(mappedBy = "idcategoria", fetch = FetchType.LAZY)
    private List<Professor> professorList;

    public Categoriaa( String descricao) {
        
        this.descricao = descricao;
        
    }

    public Categoriaa() {
    }

    public Categoriaa(Short idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Short getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Short idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Professor> getProfessorList() {
        return professorList;
    }

    public void setProfessorList(List<Professor> professorList) {
        this.professorList = professorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcategoria != null ? idcategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriaa)) {
            return false;
        }
        Categoriaa other = (Categoriaa) object;
        if ((this.idcategoria == null && other.idcategoria != null) || (this.idcategoria != null && !this.idcategoria.equals(other.idcategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Categoriaa[ idcategoria=" + idcategoria + " ]";
    }
    
}
