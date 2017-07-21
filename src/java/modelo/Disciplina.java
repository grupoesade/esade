/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "disciplina", catalog = "integra", schema = "esade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disciplina.findAll", query = "SELECT d FROM Disciplina d"),
    @NamedQuery(name = "Disciplina.findByIdDisc", query = "SELECT d FROM Disciplina d WHERE d.idDisc = :idDisc"),
    @NamedQuery(name = "Disciplina.findByCodigo", query = "SELECT d FROM Disciplina d WHERE d.codigo = :codigo"),
    @NamedQuery(name = "Disciplina.findByNome", query = "SELECT d FROM Disciplina d WHERE d.nome = :nome"),
    @NamedQuery(name = "Disciplina.findByDepartamento", query = "SELECT d FROM Disciplina d WHERE d.departamento = :departamento"),
    @NamedQuery(name = "Disciplina.findByAreaCientifica", query = "SELECT d FROM Disciplina d WHERE d.areaCientifica = :areaCientifica"),
    @NamedQuery(name = "Disciplina.findByCaracter", query = "SELECT d FROM Disciplina d WHERE d.caracter = :caracter"),
    @NamedQuery(name = "Disciplina.findByIdPeriodo", query = "SELECT d FROM Disciplina d WHERE d.idPeriodo = :idPeriodo"),
    @NamedQuery(name = "Disciplina.findByObjcetivoGeral", query = "SELECT d FROM Disciplina d WHERE d.objcetivoGeral = :objcetivoGeral"),
    @NamedQuery(name = "Disciplina.findByNivel", query = "SELECT d FROM Disciplina d WHERE d.nivel = :nivel"),
    @NamedQuery(name = "Disciplina.findBySemestre", query = "SELECT d FROM Disciplina d WHERE d.semestre = :semestre"),
    @NamedQuery(name = "Disciplina.findByCredito", query = "SELECT d FROM Disciplina d WHERE d.credito = :credito"),
    @NamedQuery(name = "Disciplina.findByAbreviatura", query = "SELECT d FROM Disciplina d WHERE d.abreviatura = :abreviatura"),
    @NamedQuery(name = "Disciplina.findByHorast", query = "SELECT d FROM Disciplina d WHERE d.horast = :horast"),
    @NamedQuery(name = "Disciplina.findByHorasp", query = "SELECT d FROM Disciplina d WHERE d.horasp = :horasp"),
    @NamedQuery(name = "Disciplina.findByNatraso", query = "SELECT d FROM Disciplina d WHERE d.natraso = :natraso")})
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disc", nullable = false)
    private Long idDisc;
    @Column(name = "codigo", length = 32)
    private String codigo;
    @Column(name = "nome", length = 200)
    private String nome;
    @Column(name = "departamento", length = 45)
    private String departamento;
    @Column(name = "area_cientifica")
    private BigInteger areaCientifica;
    @Column(name = "caracter")
    private BigInteger caracter;
    @Column(name = "id_periodo")
    private Integer idPeriodo;
    @Column(name = "objcetivo_geral", length = 200)
    private String objcetivoGeral;
    @Column(name = "nivel")
    private Integer nivel;
    @Column(name = "semestre")
    private Integer semestre;
    @Column(name = "credito")
    private Integer credito;
    @Column(name = "abreviatura", length = 11)
    private String abreviatura;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "horast", precision = 8, scale = 8)
    private Float horast;
    @Column(name = "horasp", precision = 8, scale = 8)
    private Float horasp;
    @Column(name = "natraso", length = 64)
    private String natraso;
    @OneToMany(mappedBy = "iddisc", fetch = FetchType.LAZY)
    private List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList;
    @JoinColumn(name = "curso", referencedColumnName = "id_curso")
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;
    @JoinColumn(name = "idprofessor", referencedColumnName = "idprofessor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Professor idprofessor;

    public Disciplina() {
    }

    public Disciplina(Long idDisc) {
        this.idDisc = idDisc;
    }

    public Long getIdDisc() {
        return idDisc;
    }

    public void setIdDisc(Long idDisc) {
        this.idDisc = idDisc;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public BigInteger getAreaCientifica() {
        return areaCientifica;
    }

    public void setAreaCientifica(BigInteger areaCientifica) {
        this.areaCientifica = areaCientifica;
    }

    public BigInteger getCaracter() {
        return caracter;
    }

    public void setCaracter(BigInteger caracter) {
        this.caracter = caracter;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getObjcetivoGeral() {
        return objcetivoGeral;
    }

    public void setObjcetivoGeral(String objcetivoGeral) {
        this.objcetivoGeral = objcetivoGeral;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getCredito() {
        return credito;
    }

    public void setCredito(Integer credito) {
        this.credito = credito;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Float getHorast() {
        return horast;
    }

    public void setHorast(Float horast) {
        this.horast = horast;
    }

    public Float getHorasp() {
        return horasp;
    }

    public void setHorasp(Float horasp) {
        this.horasp = horasp;
    }

    public String getNatraso() {
        return natraso;
    }

    public void setNatraso(String natraso) {
        this.natraso = natraso;
    }

    @XmlTransient
    public List<Avaliacaodocenteestudante> getAvaliacaodocenteestudanteList() {
        return avaliacaodocenteestudanteList;
    }

    public void setAvaliacaodocenteestudanteList(List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList) {
        this.avaliacaodocenteestudanteList = avaliacaodocenteestudanteList;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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
        hash += (idDisc != null ? idDisc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disciplina)) {
            return false;
        }
        Disciplina other = (Disciplina) object;
        if ((this.idDisc == null && other.idDisc != null) || (this.idDisc != null && !this.idDisc.equals(other.idDisc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Disciplina[ idDisc=" + idDisc + " ]";
    }
    
}
