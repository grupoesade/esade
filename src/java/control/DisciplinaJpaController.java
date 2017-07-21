/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Curso;
import modelo.Professor;
import modelo.Avaliacaodocenteestudante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Disciplina;

/**
 *
 * @author Paulino Francisco
 */
public class DisciplinaJpaController implements Serializable {

    public DisciplinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Disciplina disciplina) {
        if (disciplina.getAvaliacaodocenteestudanteList() == null) {
            disciplina.setAvaliacaodocenteestudanteList(new ArrayList<Avaliacaodocenteestudante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso curso = disciplina.getCurso();
            if (curso != null) {
                curso = em.getReference(curso.getClass(), curso.getIdCurso());
                disciplina.setCurso(curso);
            }
            Professor idprofessor = disciplina.getIdprofessor();
            if (idprofessor != null) {
                idprofessor = em.getReference(idprofessor.getClass(), idprofessor.getIdprofessor());
                disciplina.setIdprofessor(idprofessor);
            }
            List<Avaliacaodocenteestudante> attachedAvaliacaodocenteestudanteList = new ArrayList<Avaliacaodocenteestudante>();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach : disciplina.getAvaliacaodocenteestudanteList()) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach = em.getReference(avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach.getClass(), avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach.getIdavaliacaodocenteestudante());
                attachedAvaliacaodocenteestudanteList.add(avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach);
            }
            disciplina.setAvaliacaodocenteestudanteList(attachedAvaliacaodocenteestudanteList);
            em.persist(disciplina);
            if (curso != null) {
                curso.getDisciplinaList().add(disciplina);
                curso = em.merge(curso);
            }
            if (idprofessor != null) {
                idprofessor.getDisciplinaList().add(disciplina);
                idprofessor = em.merge(idprofessor);
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudante : disciplina.getAvaliacaodocenteestudanteList()) {
                Disciplina oldIddiscOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante = avaliacaodocenteestudanteListAvaliacaodocenteestudante.getIddisc();
                avaliacaodocenteestudanteListAvaliacaodocenteestudante.setIddisc(disciplina);
                avaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
                if (oldIddiscOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante != null) {
                    oldIddiscOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
                    oldIddiscOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(oldIddiscOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Disciplina disciplina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina persistentDisciplina = em.find(Disciplina.class, disciplina.getIdDisc());
            Curso cursoOld = persistentDisciplina.getCurso();
            Curso cursoNew = disciplina.getCurso();
            Professor idprofessorOld = persistentDisciplina.getIdprofessor();
            Professor idprofessorNew = disciplina.getIdprofessor();
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteListOld = persistentDisciplina.getAvaliacaodocenteestudanteList();
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteListNew = disciplina.getAvaliacaodocenteestudanteList();
            if (cursoNew != null) {
                cursoNew = em.getReference(cursoNew.getClass(), cursoNew.getIdCurso());
                disciplina.setCurso(cursoNew);
            }
            if (idprofessorNew != null) {
                idprofessorNew = em.getReference(idprofessorNew.getClass(), idprofessorNew.getIdprofessor());
                disciplina.setIdprofessor(idprofessorNew);
            }
            List<Avaliacaodocenteestudante> attachedAvaliacaodocenteestudanteListNew = new ArrayList<Avaliacaodocenteestudante>();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach : avaliacaodocenteestudanteListNew) {
                avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach = em.getReference(avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach.getClass(), avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach.getIdavaliacaodocenteestudante());
                attachedAvaliacaodocenteestudanteListNew.add(avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach);
            }
            avaliacaodocenteestudanteListNew = attachedAvaliacaodocenteestudanteListNew;
            disciplina.setAvaliacaodocenteestudanteList(avaliacaodocenteestudanteListNew);
            disciplina = em.merge(disciplina);
            if (cursoOld != null && !cursoOld.equals(cursoNew)) {
                cursoOld.getDisciplinaList().remove(disciplina);
                cursoOld = em.merge(cursoOld);
            }
            if (cursoNew != null && !cursoNew.equals(cursoOld)) {
                cursoNew.getDisciplinaList().add(disciplina);
                cursoNew = em.merge(cursoNew);
            }
            if (idprofessorOld != null && !idprofessorOld.equals(idprofessorNew)) {
                idprofessorOld.getDisciplinaList().remove(disciplina);
                idprofessorOld = em.merge(idprofessorOld);
            }
            if (idprofessorNew != null && !idprofessorNew.equals(idprofessorOld)) {
                idprofessorNew.getDisciplinaList().add(disciplina);
                idprofessorNew = em.merge(idprofessorNew);
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListOldAvaliacaodocenteestudante : avaliacaodocenteestudanteListOld) {
                if (!avaliacaodocenteestudanteListNew.contains(avaliacaodocenteestudanteListOldAvaliacaodocenteestudante)) {
                    avaliacaodocenteestudanteListOldAvaliacaodocenteestudante.setIddisc(null);
                    avaliacaodocenteestudanteListOldAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListOldAvaliacaodocenteestudante);
                }
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListNewAvaliacaodocenteestudante : avaliacaodocenteestudanteListNew) {
                if (!avaliacaodocenteestudanteListOld.contains(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante)) {
                    Disciplina oldIddiscOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante = avaliacaodocenteestudanteListNewAvaliacaodocenteestudante.getIddisc();
                    avaliacaodocenteestudanteListNewAvaliacaodocenteestudante.setIddisc(disciplina);
                    avaliacaodocenteestudanteListNewAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                    if (oldIddiscOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante != null && !oldIddiscOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante.equals(disciplina)) {
                        oldIddiscOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                        oldIddiscOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante = em.merge(oldIddiscOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = disciplina.getIdDisc();
                if (findDisciplina(id) == null) {
                    throw new NonexistentEntityException("The disciplina with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina disciplina;
            try {
                disciplina = em.getReference(Disciplina.class, id);
                disciplina.getIdDisc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The disciplina with id " + id + " no longer exists.", enfe);
            }
            Curso curso = disciplina.getCurso();
            if (curso != null) {
                curso.getDisciplinaList().remove(disciplina);
                curso = em.merge(curso);
            }
            Professor idprofessor = disciplina.getIdprofessor();
            if (idprofessor != null) {
                idprofessor.getDisciplinaList().remove(disciplina);
                idprofessor = em.merge(idprofessor);
            }
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList = disciplina.getAvaliacaodocenteestudanteList();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudante : avaliacaodocenteestudanteList) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudante.setIddisc(null);
                avaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
            }
            em.remove(disciplina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Disciplina> findDisciplinaEntities() {
        return findDisciplinaEntities(true, -1, -1);
    }

    public List<Disciplina> findDisciplinaEntities(int maxResults, int firstResult) {
        return findDisciplinaEntities(false, maxResults, firstResult);
    }

    private List<Disciplina> findDisciplinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Disciplina.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Disciplina findDisciplina(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Disciplina.class, id);
        } finally {
            em.close();
        }
    }

    public int getDisciplinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Disciplina> rt = cq.from(Disciplina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
