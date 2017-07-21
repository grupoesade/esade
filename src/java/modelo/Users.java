/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Paulino Francisco
 */
@Entity
@Table(name = "users", catalog = "integra", schema = "esade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUtilizador", query = "SELECT u FROM Users u WHERE u.utilizador = :utilizador"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByPasword", query = "SELECT u FROM Users u WHERE u.pasword = :pasword"),
    @NamedQuery(name = "Users.findByLastAccess", query = "SELECT u FROM Users u WHERE u.lastAccess = :lastAccess"),
    @NamedQuery(name = "Users.findByNome", query = "SELECT u FROM Users u WHERE u.nome = :nome"),
    @NamedQuery(name = "Users.findByFaculdade", query = "SELECT u FROM Users u WHERE u.faculdade = :faculdade"),
    @NamedQuery(name = "Users.findByUestudante", query = "SELECT u FROM Users u WHERE u.uestudante = :uestudante"),
    @NamedQuery(name = "Users.findByIdFuncionario", query = "SELECT u FROM Users u WHERE u.idFuncionario = :idFuncionario"),
    @NamedQuery(name = "Users.findByTenant", query = "SELECT u FROM Users u WHERE u.tenant = :tenant")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "utilizador", nullable = false, length = 45)
    private String utilizador;
    @Column(name = "email", length = 45)
    private String email;
    @Column(name = "pasword", length = 45)
    private String pasword;
    @Column(name = "last_access")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccess;
    @Column(name = "nome", length = 45)
    private String nome;
    @Column(name = "faculdade")
    private Integer faculdade;
    @Column(name = "uestudante")
    private Boolean uestudante;
    @Column(name = "id_funcionario")
    private BigInteger idFuncionario;
    @Column(name = "tenant", length = 2147483647)
    private String tenant;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private List<Usergrupo> usergrupoList;
    @JoinColumn(name = "id_estudante", referencedColumnName = "id_estudante")
    @ManyToOne(fetch = FetchType.LAZY)
    private Estudante idEstudante;
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Grupo idGrupo;
    @JoinColumn(name = "idprofessor", referencedColumnName = "idprofessor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Professor idprofessor;

    public Users() {
    }

    public Users(String utilizador) {
        this.utilizador = utilizador;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(Integer faculdade) {
        this.faculdade = faculdade;
    }

    public Boolean getUestudante() {
        return uestudante;
    }

    public void setUestudante(Boolean uestudante) {
        this.uestudante = uestudante;
    }

    public BigInteger getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(BigInteger idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    @XmlTransient
    public List<Usergrupo> getUsergrupoList() {
        return usergrupoList;
    }

    public void setUsergrupoList(List<Usergrupo> usergrupoList) {
        this.usergrupoList = usergrupoList;
    }

    public Estudante getIdEstudante() {
        return idEstudante;
    }

    public void setIdEstudante(Estudante idEstudante) {
        this.idEstudante = idEstudante;
    }

    public Grupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Grupo idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Professor getIdprofessor() {
        return idprofessor;
    }

    public void setIdprofessor(Professor idprofessor) {
        this.idprofessor = idprofessor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (utilizador != null ? utilizador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.utilizador == null && other.utilizador != null) || (this.utilizador != null && !this.utilizador.equals(other.utilizador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Users[ utilizador=" + utilizador + " ]";
    }
    
}
