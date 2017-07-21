/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Paulino Francisco
 */
@Entity
@Table(name = "matricula", catalog = "integra", schema = "esade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matricula.findAll", query = "SELECT m FROM Matricula m"),
    @NamedQuery(name = "Matricula.findByIdEstudante", query = "SELECT m FROM Matricula m WHERE m.matriculaPK.idEstudante = :idEstudante"),
    @NamedQuery(name = "Matricula.findByAno", query = "SELECT m FROM Matricula m WHERE m.matriculaPK.ano = :ano"),
    @NamedQuery(name = "Matricula.findByDataMatricula", query = "SELECT m FROM Matricula m WHERE m.dataMatricula = :dataMatricula"),
    @NamedQuery(name = "Matricula.findByConfirmacao", query = "SELECT m FROM Matricula m WHERE m.confirmacao = :confirmacao"),
    @NamedQuery(name = "Matricula.findByModoMatricula", query = "SELECT m FROM Matricula m WHERE m.modoMatricula = :modoMatricula"),
    @NamedQuery(name = "Matricula.findByFuncionario", query = "SELECT m FROM Matricula m WHERE m.funcionario = :funcionario"),
    @NamedQuery(name = "Matricula.findByValor", query = "SELECT m FROM Matricula m WHERE m.valor = :valor"),
    @NamedQuery(name = "Matricula.findByEstado", query = "SELECT m FROM Matricula m WHERE m.estado = :estado"),
    @NamedQuery(name = "Matricula.findByAnulada", query = "SELECT m FROM Matricula m WHERE m.anulada = :anulada"),
    @NamedQuery(name = "Matricula.findByMulta", query = "SELECT m FROM Matricula m WHERE m.multa = :multa"),
    @NamedQuery(name = "Matricula.findByObs", query = "SELECT m FROM Matricula m WHERE m.obs = :obs"),
    @NamedQuery(name = "Matricula.findByPeriodo", query = "SELECT m FROM Matricula m WHERE m.periodo = :periodo"),
    @NamedQuery(name = "Matricula.findByEmailenviado", query = "SELECT m FROM Matricula m WHERE m.emailenviado = :emailenviado"),
    @NamedQuery(name = "Matricula.findByVersion", query = "SELECT m FROM Matricula m WHERE m.version = :version")})
public class Matricula implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MatriculaPK matriculaPK;
    @Column(name = "data_matricula")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMatricula;
    @Column(name = "confirmacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmacao;
    @Column(name = "modo_matricula")
    private Integer modoMatricula;
    @Column(name = "funcionario")
    private BigInteger funcionario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor", precision = 8, scale = 8)
    private Float valor;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "anulada")
    private Boolean anulada;
    @Column(name = "multa", precision = 8, scale = 8)
    private Float multa;
    @Column(name = "obs", length = 2147483647)
    private String obs;
    @Column(name = "periodo", length = 2147483647)
    private String periodo;
    @Column(name = "emailenviado")
    private Boolean emailenviado;
    @Column(name = "version")
    private Short version;
    @JoinColumn(name = "curso", referencedColumnName = "id_curso", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Curso curso;
    @JoinColumn(name = "id_estudante", referencedColumnName = "id_estudante", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estudante estudante;

    public Matricula() {
    }

    public Matricula(MatriculaPK matriculaPK) {
        this.matriculaPK = matriculaPK;
    }

    public Matricula(long idEstudante, int ano) {
        this.matriculaPK = new MatriculaPK(idEstudante, ano);
    }

    public MatriculaPK getMatriculaPK() {
        return matriculaPK;
    }

    public void setMatriculaPK(MatriculaPK matriculaPK) {
        this.matriculaPK = matriculaPK;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Date getConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(Date confirmacao) {
        this.confirmacao = confirmacao;
    }

    public Integer getModoMatricula() {
        return modoMatricula;
    }

    public void setModoMatricula(Integer modoMatricula) {
        this.modoMatricula = modoMatricula;
    }

    public BigInteger getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(BigInteger funcionario) {
        this.funcionario = funcionario;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Boolean getAnulada() {
        return anulada;
    }

    public void setAnulada(Boolean anulada) {
        this.anulada = anulada;
    }

    public Float getMulta() {
        return multa;
    }

    public void setMulta(Float multa) {
        this.multa = multa;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Boolean getEmailenviado() {
        return emailenviado;
    }

    public void setEmailenviado(Boolean emailenviado) {
        this.emailenviado = emailenviado;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matriculaPK != null ? matriculaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matricula)) {
            return false;
        }
        Matricula other = (Matricula) object;
        if ((this.matriculaPK == null && other.matriculaPK != null) || (this.matriculaPK != null && !this.matriculaPK.equals(other.matriculaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Matricula[ matriculaPK=" + matriculaPK + " ]";
    }
    
}
