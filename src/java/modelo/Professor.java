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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "professor", catalog = "integra", schema = "esade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Professor.findAll", query = "SELECT p FROM Professor p"),
    @NamedQuery(name = "Professor.findByNome", query = "SELECT p FROM Professor p WHERE p.nome = :nome"),
    @NamedQuery(name = "Professor.findByIdprofessor", query = "SELECT p FROM Professor p WHERE p.idprofessor = :idprofessor")})
public class Professor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "nome", length = 50)
    private String nome;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprofessor", nullable = false)
    private Short idprofessor;
    @OneToMany(mappedBy = "idprofessor", fetch = FetchType.LAZY)
    private List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList;
    @OneToMany(mappedBy = "idprofessor", fetch = FetchType.LAZY)
    private List<Autoavaliacao> autoavaliacaoList;
    @OneToMany(mappedBy = "idprofessor", fetch = FetchType.LAZY)
    private List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList;
    @OneToMany(mappedBy = "idprofessor", fetch = FetchType.LAZY)
    private List<Disciplina> disciplinaList;
    @OneToMany(mappedBy = "idprofessor", fetch = FetchType.LAZY)
    private List<Users> usersList;
    @JoinColumn(name = "idcargochefia", referencedColumnName = "idcargochefia")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cargochefia idcargochefia;
    @JoinColumn(name = "idcategoria", referencedColumnName = "idcategoria")
    @ManyToOne(fetch = FetchType.LAZY)
    private Categoriaa idcategoria;
    @JoinColumn(name = "iddepartamento", referencedColumnName = "iddepartamento")
    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento iddepartamento;
    @JoinColumn(name = "idregime", referencedColumnName = "idregime")
    @ManyToOne(fetch = FetchType.LAZY)
    private Regime idregime;

    public Professor() {
    }

    public Professor(Short idprofessor) {
        this.idprofessor = idprofessor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Short getIdprofessor() {
        return idprofessor;
    }

    public void setIdprofessor(Short idprofessor) {
        this.idprofessor = idprofessor;
    }

    @XmlTransient
    public List<Avaliacaodocentesuperior> getAvaliacaodocentesuperiorList() {
        return avaliacaodocentesuperiorList;
    }

    public void setAvaliacaodocentesuperiorList(List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList) {
        this.avaliacaodocentesuperiorList = avaliacaodocentesuperiorList;
    }

    @XmlTransient
    public List<Autoavaliacao> getAutoavaliacaoList() {
        return autoavaliacaoList;
    }

    public void setAutoavaliacaoList(List<Autoavaliacao> autoavaliacaoList) {
        this.autoavaliacaoList = autoavaliacaoList;
    }

    @XmlTransient
    public List<Avaliacaodocenteestudante> getAvaliacaodocenteestudanteList() {
        return avaliacaodocenteestudanteList;
    }

    public void setAvaliacaodocenteestudanteList(List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList) {
        this.avaliacaodocenteestudanteList = avaliacaodocenteestudanteList;
    }

    @XmlTransient
    public List<Disciplina> getDisciplinaList() {
        return disciplinaList;
    }

    public void setDisciplinaList(List<Disciplina> disciplinaList) {
        this.disciplinaList = disciplinaList;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Cargochefia getIdcargochefia() {
        return idcargochefia;
    }

    public void setIdcargochefia(Cargochefia idcargochefia) {
        this.idcargochefia = idcargochefia;
    }

    public Categoriaa getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Categoriaa idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Departamento getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(Departamento iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public Regime getIdregime() {
        return idregime;
    }

    public void setIdregime(Regime idregime) {
        this.idregime = idregime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprofessor != null ? idprofessor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Professor)) {
            return false;
        }
        Professor other = (Professor) object;
        if ((this.idprofessor == null && other.idprofessor != null) || (this.idprofessor != null && !this.idprofessor.equals(other.idprofessor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Professor[ idprofessor=" + idprofessor + " ]";
    }
    
}
