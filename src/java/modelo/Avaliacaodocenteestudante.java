/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "avaliacaodocenteestudante", catalog = "integra", schema = "esade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Avaliacaodocenteestudante.findAll", query = "SELECT a FROM Avaliacaodocenteestudante a"),
    @NamedQuery(name = "Avaliacaodocenteestudante.findByIdavaliacaodocenteestudante", query = "SELECT a FROM Avaliacaodocenteestudante a WHERE a.idavaliacaodocenteestudante = :idavaliacaodocenteestudante"),
    @NamedQuery(name = "Avaliacaodocenteestudante.findByObservacao", query = "SELECT a FROM Avaliacaodocenteestudante a WHERE a.observacao = :observacao"),
    @NamedQuery(name = "Avaliacaodocenteestudante.findByPontuacaototal", query = "SELECT a FROM Avaliacaodocenteestudante a WHERE a.pontuacaototal = :pontuacaototal"),
    @NamedQuery(name = "Avaliacaodocenteestudante.findByAno", query = "SELECT a FROM Avaliacaodocenteestudante a WHERE a.ano = :ano")})
public class Avaliacaodocenteestudante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idavaliacaodocenteestudante", nullable = false)
    private Short idavaliacaodocenteestudante;
    @Column(name = "observacao", length = 255)
    private String observacao;
    @Column(name = "pontuacaototal")
    private Short pontuacaototal;
    @Column(name = "ano")
    @Temporal(TemporalType.DATE)
    private Date ano;
    @JoinTable(name = "avaliacaoquestaode", joinColumns = {
        @JoinColumn(name = "idavaliacaodocenteestudante", referencedColumnName = "idavaliacaodocenteestudante", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "idquestaodocenteestudante", referencedColumnName = "idquestaodocenteestudante", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Questaodocenteestudante> questaodocenteestudanteList;
    @JoinTable(name = "docentepeloestudantepontuacao", joinColumns = {
        @JoinColumn(name = "idavaliacaodocenteestudante", referencedColumnName = "idavaliacaodocenteestudante", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "idpontuacaoconjugada", referencedColumnName = "idpontuacaoconjugada", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Pontuacaoconjugada> pontuacaoconjugadaList;
    @JoinColumn(name = "iddisc", referencedColumnName = "id_disc")
    @ManyToOne(fetch = FetchType.LAZY)
    private Disciplina iddisc;
    @JoinColumn(name = "idprofessor", referencedColumnName = "idprofessor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Professor idprofessor;

    public Avaliacaodocenteestudante() {
    }

    public Avaliacaodocenteestudante(Short idavaliacaodocenteestudante) {
        this.idavaliacaodocenteestudante = idavaliacaodocenteestudante;
    }

    public Short getIdavaliacaodocenteestudante() {
        return idavaliacaodocenteestudante;
    }

    public void setIdavaliacaodocenteestudante(Short idavaliacaodocenteestudante) {
        this.idavaliacaodocenteestudante = idavaliacaodocenteestudante;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Short getPontuacaototal() {
        return pontuacaototal;
    }

    public void setPontuacaototal(Short pontuacaototal) {
        this.pontuacaototal = pontuacaototal;
    }

    public Date getAno() {
        return ano;
    }

    public void setAno(Date ano) {
        this.ano = ano;
    }

    @XmlTransient
    public List<Questaodocenteestudante> getQuestaodocenteestudanteList() {
        return questaodocenteestudanteList;
    }

    public void setQuestaodocenteestudanteList(List<Questaodocenteestudante> questaodocenteestudanteList) {
        this.questaodocenteestudanteList = questaodocenteestudanteList;
    }

    @XmlTransient
    public List<Pontuacaoconjugada> getPontuacaoconjugadaList() {
        return pontuacaoconjugadaList;
    }

    public void setPontuacaoconjugadaList(List<Pontuacaoconjugada> pontuacaoconjugadaList) {
        this.pontuacaoconjugadaList = pontuacaoconjugadaList;
    }

    public Disciplina getIddisc() {
        return iddisc;
    }

    public void setIddisc(Disciplina iddisc) {
        this.iddisc = iddisc;
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
        hash += (idavaliacaodocenteestudante != null ? idavaliacaodocenteestudante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avaliacaodocenteestudante)) {
            return false;
        }
        Avaliacaodocenteestudante other = (Avaliacaodocenteestudante) object;
        if ((this.idavaliacaodocenteestudante == null && other.idavaliacaodocenteestudante != null) || (this.idavaliacaodocenteestudante != null && !this.idavaliacaodocenteestudante.equals(other.idavaliacaodocenteestudante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Avaliacaodocenteestudante[ idavaliacaodocenteestudante=" + idavaliacaodocenteestudante + " ]";
    }
    
}
