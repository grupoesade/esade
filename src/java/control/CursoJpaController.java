/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Disciplina;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Curso;
import modelo.Matricula;
import modelo.Estudante;

/**
 *
 * @author Paulino Francisco
 */
public class CursoJpaController implements Serializable {

    public CursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) {
        if (curso.getDisciplinaList() == null) {
            curso.setDisciplinaList(new ArrayList<Disciplina>());
        }
        if (curso.getMatriculaList() == null) {
            curso.setMatriculaList(new ArrayList<Matricula>());
        }
        if (curso.getEstudanteList() == null) {
            curso.setEstudanteList(new ArrayList<Estudante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Disciplina> attachedDisciplinaList = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListDisciplinaToAttach : curso.getDisciplinaList()) {
                disciplinaListDisciplinaToAttach = em.getReference(disciplinaListDisciplinaToAttach.getClass(), disciplinaListDisciplinaToAttach.getIdDisc());
                attachedDisciplinaList.add(disciplinaListDisciplinaToAttach);
            }
            curso.setDisciplinaList(attachedDisciplinaList);
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : curso.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getMatriculaPK());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            curso.setMatriculaList(attachedMatriculaList);
            List<Estudante> attachedEstudanteList = new ArrayList<Estudante>();
            for (Estudante estudanteListEstudanteToAttach : curso.getEstudanteList()) {
                estudanteListEstudanteToAttach = em.getReference(estudanteListEstudanteToAttach.getClass(), estudanteListEstudanteToAttach.getIdEstudante());
                attachedEstudanteList.add(estudanteListEstudanteToAttach);
            }
            curso.setEstudanteList(attachedEstudanteList);
            em.persist(curso);
            for (Disciplina disciplinaListDisciplina : curso.getDisciplinaList()) {
                Curso oldCursoOfDisciplinaListDisciplina = disciplinaListDisciplina.getCurso();
                disciplinaListDisciplina.setCurso(curso);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
                if (oldCursoOfDisciplinaListDisciplina != null) {
                    oldCursoOfDisciplinaListDisciplina.getDisciplinaList().remove(disciplinaListDisciplina);
                    oldCursoOfDisciplinaListDisciplina = em.merge(oldCursoOfDisciplinaListDisciplina);
                }
            }
            for (Matricula matriculaListMatricula : curso.getMatriculaList()) {
                Curso oldCursoOfMatriculaListMatricula = matriculaListMatricula.getCurso();
                matriculaListMatricula.setCurso(curso);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldCursoOfMatriculaListMatricula != null) {
                    oldCursoOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldCursoOfMatriculaListMatricula = em.merge(oldCursoOfMatriculaListMatricula);
                }
            }
            for (Estudante estudanteListEstudante : curso.getEstudanteList()) {
                Curso oldIdCursoOfEstudanteListEstudante = estudanteListEstudante.getIdCurso();
                estudanteListEstudante.setIdCurso(curso);
                estudanteListEstudante = em.merge(estudanteListEstudante);
                if (oldIdCursoOfEstudanteListEstudante != null) {
                    oldIdCursoOfEstudanteListEstudante.getEstudanteList().remove(estudanteListEstudante);
                    oldIdCursoOfEstudanteListEstudante = em.merge(oldIdCursoOfEstudanteListEstudante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curso curso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso persistentCurso = em.find(Curso.class, curso.getIdCurso());
            List<Disciplina> disciplinaListOld = persistentCurso.getDisciplinaList();
            List<Disciplina> disciplinaListNew = curso.getDisciplinaList();
            List<Matricula> matriculaListOld = persistentCurso.getMatriculaList();
            List<Matricula> matriculaListNew = curso.getMatriculaList();
            List<Estudante> estudanteListOld = persistentCurso.getEstudanteList();
            List<Estudante> estudanteListNew = curso.getEstudanteList();
            List<String> illegalOrphanMessages = null;
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its curso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Disciplina> attachedDisciplinaListNew = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListNewDisciplinaToAttach : disciplinaListNew) {
                disciplinaListNewDisciplinaToAttach = em.getReference(disciplinaListNewDisciplinaToAttach.getClass(), disciplinaListNewDisciplinaToAttach.getIdDisc());
                attachedDisciplinaListNew.add(disciplinaListNewDisciplinaToAttach);
            }
            disciplinaListNew = attachedDisciplinaListNew;
            curso.setDisciplinaList(disciplinaListNew);
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getMatriculaPK());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            curso.setMatriculaList(matriculaListNew);
            List<Estudante> attachedEstudanteListNew = new ArrayList<Estudante>();
            for (Estudante estudanteListNewEstudanteToAttach : estudanteListNew) {
                estudanteListNewEstudanteToAttach = em.getReference(estudanteListNewEstudanteToAttach.getClass(), estudanteListNewEstudanteToAttach.getIdEstudante());
                attachedEstudanteListNew.add(estudanteListNewEstudanteToAttach);
            }
            estudanteListNew = attachedEstudanteListNew;
            curso.setEstudanteList(estudanteListNew);
            curso = em.merge(curso);
            for (Disciplina disciplinaListOldDisciplina : disciplinaListOld) {
                if (!disciplinaListNew.contains(disciplinaListOldDisciplina)) {
                    disciplinaListOldDisciplina.setCurso(null);
                    disciplinaListOldDisciplina = em.merge(disciplinaListOldDisciplina);
                }
            }
            for (Disciplina disciplinaListNewDisciplina : disciplinaListNew) {
                if (!disciplinaListOld.contains(disciplinaListNewDisciplina)) {
                    Curso oldCursoOfDisciplinaListNewDisciplina = disciplinaListNewDisciplina.getCurso();
                    disciplinaListNewDisciplina.setCurso(curso);
                    disciplinaListNewDisciplina = em.merge(disciplinaListNewDisciplina);
                    if (oldCursoOfDisciplinaListNewDisciplina != null && !oldCursoOfDisciplinaListNewDisciplina.equals(curso)) {
                        oldCursoOfDisciplinaListNewDisciplina.getDisciplinaList().remove(disciplinaListNewDisciplina);
                        oldCursoOfDisciplinaListNewDisciplina = em.merge(oldCursoOfDisciplinaListNewDisciplina);
                    }
                }
            }
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Curso oldCursoOfMatriculaListNewMatricula = matriculaListNewMatricula.getCurso();
                    matriculaListNewMatricula.setCurso(curso);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldCursoOfMatriculaListNewMatricula != null && !oldCursoOfMatriculaListNewMatricula.equals(curso)) {
                        oldCursoOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldCursoOfMatriculaListNewMatricula = em.merge(oldCursoOfMatriculaListNewMatricula);
                    }
                }
            }
            for (Estudante estudanteListOldEstudante : estudanteListOld) {
                if (!estudanteListNew.contains(estudanteListOldEstudante)) {
                    estudanteListOldEstudante.setIdCurso(null);
                    estudanteListOldEstudante = em.merge(estudanteListOldEstudante);
                }
            }
            for (Estudante estudanteListNewEstudante : estudanteListNew) {
                if (!estudanteListOld.contains(estudanteListNewEstudante)) {
                    Curso oldIdCursoOfEstudanteListNewEstudante = estudanteListNewEstudante.getIdCurso();
                    estudanteListNewEstudante.setIdCurso(curso);
                    estudanteListNewEstudante = em.merge(estudanteListNewEstudante);
                    if (oldIdCursoOfEstudanteListNewEstudante != null && !oldIdCursoOfEstudanteListNewEstudante.equals(curso)) {
                        oldIdCursoOfEstudanteListNewEstudante.getEstudanteList().remove(estudanteListNewEstudante);
                        oldIdCursoOfEstudanteListNewEstudante = em.merge(oldIdCursoOfEstudanteListNewEstudante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = curso.getIdCurso();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getIdCurso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Matricula> matriculaListOrphanCheck = curso.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the Matricula " + matriculaListOrphanCheckMatricula + " in its matriculaList field has a non-nullable curso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Disciplina> disciplinaList = curso.getDisciplinaList();
            for (Disciplina disciplinaListDisciplina : disciplinaList) {
                disciplinaListDisciplina.setCurso(null);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
            }
            List<Estudante> estudanteList = curso.getEstudanteList();
            for (Estudante estudanteListEstudante : estudanteList) {
                estudanteListEstudante.setIdCurso(null);
                estudanteListEstudante = em.merge(estudanteListEstudante);
            }
            em.remove(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
