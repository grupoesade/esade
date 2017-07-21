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
import modelo.Cargochefia;
import modelo.Categoriaa;
import modelo.Departamento;
import modelo.Regime;
import modelo.Avaliacaodocentesuperior;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Autoavaliacao;
import modelo.Avaliacaodocenteestudante;
import modelo.Disciplina;
import modelo.Professor;
import modelo.Users;

/**
 *
 * @author Paulino Francisco
 */
public class ProfessorJpaController implements Serializable {

    public ProfessorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Professor professor) {
        if (professor.getAvaliacaodocentesuperiorList() == null) {
            professor.setAvaliacaodocentesuperiorList(new ArrayList<Avaliacaodocentesuperior>());
        }
        if (professor.getAutoavaliacaoList() == null) {
            professor.setAutoavaliacaoList(new ArrayList<Autoavaliacao>());
        }
        if (professor.getAvaliacaodocenteestudanteList() == null) {
            professor.setAvaliacaodocenteestudanteList(new ArrayList<Avaliacaodocenteestudante>());
        }
        if (professor.getDisciplinaList() == null) {
            professor.setDisciplinaList(new ArrayList<Disciplina>());
        }
        if (professor.getUsersList() == null) {
            professor.setUsersList(new ArrayList<Users>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargochefia idcargochefia = professor.getIdcargochefia();
            if (idcargochefia != null) {
                idcargochefia = em.getReference(idcargochefia.getClass(), idcargochefia.getIdcargochefia());
                professor.setIdcargochefia(idcargochefia);
            }
            Categoriaa idcategoria = professor.getIdcategoria();
            if (idcategoria != null) {
                idcategoria = em.getReference(idcategoria.getClass(), idcategoria.getIdcategoria());
                professor.setIdcategoria(idcategoria);
            }
            Departamento iddepartamento = professor.getIddepartamento();
            if (iddepartamento != null) {
                iddepartamento = em.getReference(iddepartamento.getClass(), iddepartamento.getIddepartamento());
                professor.setIddepartamento(iddepartamento);
            }
            Regime idregime = professor.getIdregime();
            if (idregime != null) {
                idregime = em.getReference(idregime.getClass(), idregime.getIdregime());
                professor.setIdregime(idregime);
            }
            List<Avaliacaodocentesuperior> attachedAvaliacaodocentesuperiorList = new ArrayList<Avaliacaodocentesuperior>();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach : professor.getAvaliacaodocentesuperiorList()) {
                avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach = em.getReference(avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach.getClass(), avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach.getIdavaliacaodocentesuperior());
                attachedAvaliacaodocentesuperiorList.add(avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach);
            }
            professor.setAvaliacaodocentesuperiorList(attachedAvaliacaodocentesuperiorList);
            List<Autoavaliacao> attachedAutoavaliacaoList = new ArrayList<Autoavaliacao>();
            for (Autoavaliacao autoavaliacaoListAutoavaliacaoToAttach : professor.getAutoavaliacaoList()) {
                autoavaliacaoListAutoavaliacaoToAttach = em.getReference(autoavaliacaoListAutoavaliacaoToAttach.getClass(), autoavaliacaoListAutoavaliacaoToAttach.getIdautoavaliacao());
                attachedAutoavaliacaoList.add(autoavaliacaoListAutoavaliacaoToAttach);
            }
            professor.setAutoavaliacaoList(attachedAutoavaliacaoList);
            List<Avaliacaodocenteestudante> attachedAvaliacaodocenteestudanteList = new ArrayList<Avaliacaodocenteestudante>();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach : professor.getAvaliacaodocenteestudanteList()) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach = em.getReference(avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach.getClass(), avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach.getIdavaliacaodocenteestudante());
                attachedAvaliacaodocenteestudanteList.add(avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach);
            }
            professor.setAvaliacaodocenteestudanteList(attachedAvaliacaodocenteestudanteList);
            List<Disciplina> attachedDisciplinaList = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListDisciplinaToAttach : professor.getDisciplinaList()) {
                disciplinaListDisciplinaToAttach = em.getReference(disciplinaListDisciplinaToAttach.getClass(), disciplinaListDisciplinaToAttach.getIdDisc());
                attachedDisciplinaList.add(disciplinaListDisciplinaToAttach);
            }
            professor.setDisciplinaList(attachedDisciplinaList);
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : professor.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUtilizador());
                attachedUsersList.add(usersListUsersToAttach);
            }
            professor.setUsersList(attachedUsersList);
            em.persist(professor);
            if (idcargochefia != null) {
                idcargochefia.getProfessorList().add(professor);
                idcargochefia = em.merge(idcargochefia);
            }
            if (idcategoria != null) {
                idcategoria.getProfessorList().add(professor);
                idcategoria = em.merge(idcategoria);
            }
            if (iddepartamento != null) {
                iddepartamento.getProfessorList().add(professor);
                iddepartamento = em.merge(iddepartamento);
            }
            if (idregime != null) {
                idregime.getProfessorList().add(professor);
                idregime = em.merge(idregime);
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperior : professor.getAvaliacaodocentesuperiorList()) {
                Professor oldIdprofessorOfAvaliacaodocentesuperiorListAvaliacaodocentesuperior = avaliacaodocentesuperiorListAvaliacaodocentesuperior.getIdprofessor();
                avaliacaodocentesuperiorListAvaliacaodocentesuperior.setIdprofessor(professor);
                avaliacaodocentesuperiorListAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListAvaliacaodocentesuperior);
                if (oldIdprofessorOfAvaliacaodocentesuperiorListAvaliacaodocentesuperior != null) {
                    oldIdprofessorOfAvaliacaodocentesuperiorListAvaliacaodocentesuperior.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperiorListAvaliacaodocentesuperior);
                    oldIdprofessorOfAvaliacaodocentesuperiorListAvaliacaodocentesuperior = em.merge(oldIdprofessorOfAvaliacaodocentesuperiorListAvaliacaodocentesuperior);
                }
            }
            for (Autoavaliacao autoavaliacaoListAutoavaliacao : professor.getAutoavaliacaoList()) {
                Professor oldIdprofessorOfAutoavaliacaoListAutoavaliacao = autoavaliacaoListAutoavaliacao.getIdprofessor();
                autoavaliacaoListAutoavaliacao.setIdprofessor(professor);
                autoavaliacaoListAutoavaliacao = em.merge(autoavaliacaoListAutoavaliacao);
                if (oldIdprofessorOfAutoavaliacaoListAutoavaliacao != null) {
                    oldIdprofessorOfAutoavaliacaoListAutoavaliacao.getAutoavaliacaoList().remove(autoavaliacaoListAutoavaliacao);
                    oldIdprofessorOfAutoavaliacaoListAutoavaliacao = em.merge(oldIdprofessorOfAutoavaliacaoListAutoavaliacao);
                }
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudante : professor.getAvaliacaodocenteestudanteList()) {
                Professor oldIdprofessorOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante = avaliacaodocenteestudanteListAvaliacaodocenteestudante.getIdprofessor();
                avaliacaodocenteestudanteListAvaliacaodocenteestudante.setIdprofessor(professor);
                avaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
                if (oldIdprofessorOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante != null) {
                    oldIdprofessorOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
                    oldIdprofessorOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(oldIdprofessorOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante);
                }
            }
            for (Disciplina disciplinaListDisciplina : professor.getDisciplinaList()) {
                Professor oldIdprofessorOfDisciplinaListDisciplina = disciplinaListDisciplina.getIdprofessor();
                disciplinaListDisciplina.setIdprofessor(professor);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
                if (oldIdprofessorOfDisciplinaListDisciplina != null) {
                    oldIdprofessorOfDisciplinaListDisciplina.getDisciplinaList().remove(disciplinaListDisciplina);
                    oldIdprofessorOfDisciplinaListDisciplina = em.merge(oldIdprofessorOfDisciplinaListDisciplina);
                }
            }
            for (Users usersListUsers : professor.getUsersList()) {
                Professor oldIdprofessorOfUsersListUsers = usersListUsers.getIdprofessor();
                usersListUsers.setIdprofessor(professor);
                usersListUsers = em.merge(usersListUsers);
                if (oldIdprofessorOfUsersListUsers != null) {
                    oldIdprofessorOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldIdprofessorOfUsersListUsers = em.merge(oldIdprofessorOfUsersListUsers);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Professor professor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professor persistentProfessor = em.find(Professor.class, professor.getIdprofessor());
            Cargochefia idcargochefiaOld = persistentProfessor.getIdcargochefia();
            Cargochefia idcargochefiaNew = professor.getIdcargochefia();
            Categoriaa idcategoriaOld = persistentProfessor.getIdcategoria();
            Categoriaa idcategoriaNew = professor.getIdcategoria();
            Departamento iddepartamentoOld = persistentProfessor.getIddepartamento();
            Departamento iddepartamentoNew = professor.getIddepartamento();
            Regime idregimeOld = persistentProfessor.getIdregime();
            Regime idregimeNew = professor.getIdregime();
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorListOld = persistentProfessor.getAvaliacaodocentesuperiorList();
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorListNew = professor.getAvaliacaodocentesuperiorList();
            List<Autoavaliacao> autoavaliacaoListOld = persistentProfessor.getAutoavaliacaoList();
            List<Autoavaliacao> autoavaliacaoListNew = professor.getAutoavaliacaoList();
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteListOld = persistentProfessor.getAvaliacaodocenteestudanteList();
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteListNew = professor.getAvaliacaodocenteestudanteList();
            List<Disciplina> disciplinaListOld = persistentProfessor.getDisciplinaList();
            List<Disciplina> disciplinaListNew = professor.getDisciplinaList();
            List<Users> usersListOld = persistentProfessor.getUsersList();
            List<Users> usersListNew = professor.getUsersList();
            if (idcargochefiaNew != null) {
                idcargochefiaNew = em.getReference(idcargochefiaNew.getClass(), idcargochefiaNew.getIdcargochefia());
                professor.setIdcargochefia(idcargochefiaNew);
            }
            if (idcategoriaNew != null) {
                idcategoriaNew = em.getReference(idcategoriaNew.getClass(), idcategoriaNew.getIdcategoria());
                professor.setIdcategoria(idcategoriaNew);
            }
            if (iddepartamentoNew != null) {
                iddepartamentoNew = em.getReference(iddepartamentoNew.getClass(), iddepartamentoNew.getIddepartamento());
                professor.setIddepartamento(iddepartamentoNew);
            }
            if (idregimeNew != null) {
                idregimeNew = em.getReference(idregimeNew.getClass(), idregimeNew.getIdregime());
                professor.setIdregime(idregimeNew);
            }
            List<Avaliacaodocentesuperior> attachedAvaliacaodocentesuperiorListNew = new ArrayList<Avaliacaodocentesuperior>();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach : avaliacaodocentesuperiorListNew) {
                avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach = em.getReference(avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach.getClass(), avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach.getIdavaliacaodocentesuperior());
                attachedAvaliacaodocentesuperiorListNew.add(avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach);
            }
            avaliacaodocentesuperiorListNew = attachedAvaliacaodocentesuperiorListNew;
            professor.setAvaliacaodocentesuperiorList(avaliacaodocentesuperiorListNew);
            List<Autoavaliacao> attachedAutoavaliacaoListNew = new ArrayList<Autoavaliacao>();
            for (Autoavaliacao autoavaliacaoListNewAutoavaliacaoToAttach : autoavaliacaoListNew) {
                autoavaliacaoListNewAutoavaliacaoToAttach = em.getReference(autoavaliacaoListNewAutoavaliacaoToAttach.getClass(), autoavaliacaoListNewAutoavaliacaoToAttach.getIdautoavaliacao());
                attachedAutoavaliacaoListNew.add(autoavaliacaoListNewAutoavaliacaoToAttach);
            }
            autoavaliacaoListNew = attachedAutoavaliacaoListNew;
            professor.setAutoavaliacaoList(autoavaliacaoListNew);
            List<Avaliacaodocenteestudante> attachedAvaliacaodocenteestudanteListNew = new ArrayList<Avaliacaodocenteestudante>();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach : avaliacaodocenteestudanteListNew) {
                avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach = em.getReference(avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach.getClass(), avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach.getIdavaliacaodocenteestudante());
                attachedAvaliacaodocenteestudanteListNew.add(avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach);
            }
            avaliacaodocenteestudanteListNew = attachedAvaliacaodocenteestudanteListNew;
            professor.setAvaliacaodocenteestudanteList(avaliacaodocenteestudanteListNew);
            List<Disciplina> attachedDisciplinaListNew = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListNewDisciplinaToAttach : disciplinaListNew) {
                disciplinaListNewDisciplinaToAttach = em.getReference(disciplinaListNewDisciplinaToAttach.getClass(), disciplinaListNewDisciplinaToAttach.getIdDisc());
                attachedDisciplinaListNew.add(disciplinaListNewDisciplinaToAttach);
            }
            disciplinaListNew = attachedDisciplinaListNew;
            professor.setDisciplinaList(disciplinaListNew);
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUtilizador());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            professor.setUsersList(usersListNew);
            professor = em.merge(professor);
            if (idcargochefiaOld != null && !idcargochefiaOld.equals(idcargochefiaNew)) {
                idcargochefiaOld.getProfessorList().remove(professor);
                idcargochefiaOld = em.merge(idcargochefiaOld);
            }
            if (idcargochefiaNew != null && !idcargochefiaNew.equals(idcargochefiaOld)) {
                idcargochefiaNew.getProfessorList().add(professor);
                idcargochefiaNew = em.merge(idcargochefiaNew);
            }
            if (idcategoriaOld != null && !idcategoriaOld.equals(idcategoriaNew)) {
                idcategoriaOld.getProfessorList().remove(professor);
                idcategoriaOld = em.merge(idcategoriaOld);
            }
            if (idcategoriaNew != null && !idcategoriaNew.equals(idcategoriaOld)) {
                idcategoriaNew.getProfessorList().add(professor);
                idcategoriaNew = em.merge(idcategoriaNew);
            }
            if (iddepartamentoOld != null && !iddepartamentoOld.equals(iddepartamentoNew)) {
                iddepartamentoOld.getProfessorList().remove(professor);
                iddepartamentoOld = em.merge(iddepartamentoOld);
            }
            if (iddepartamentoNew != null && !iddepartamentoNew.equals(iddepartamentoOld)) {
                iddepartamentoNew.getProfessorList().add(professor);
                iddepartamentoNew = em.merge(iddepartamentoNew);
            }
            if (idregimeOld != null && !idregimeOld.equals(idregimeNew)) {
                idregimeOld.getProfessorList().remove(professor);
                idregimeOld = em.merge(idregimeOld);
            }
            if (idregimeNew != null && !idregimeNew.equals(idregimeOld)) {
                idregimeNew.getProfessorList().add(professor);
                idregimeNew = em.merge(idregimeNew);
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListOldAvaliacaodocentesuperior : avaliacaodocentesuperiorListOld) {
                if (!avaliacaodocentesuperiorListNew.contains(avaliacaodocentesuperiorListOldAvaliacaodocentesuperior)) {
                    avaliacaodocentesuperiorListOldAvaliacaodocentesuperior.setIdprofessor(null);
                    avaliacaodocentesuperiorListOldAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListOldAvaliacaodocentesuperior);
                }
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListNewAvaliacaodocentesuperior : avaliacaodocentesuperiorListNew) {
                if (!avaliacaodocentesuperiorListOld.contains(avaliacaodocentesuperiorListNewAvaliacaodocentesuperior)) {
                    Professor oldIdprofessorOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior = avaliacaodocentesuperiorListNewAvaliacaodocentesuperior.getIdprofessor();
                    avaliacaodocentesuperiorListNewAvaliacaodocentesuperior.setIdprofessor(professor);
                    avaliacaodocentesuperiorListNewAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListNewAvaliacaodocentesuperior);
                    if (oldIdprofessorOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior != null && !oldIdprofessorOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior.equals(professor)) {
                        oldIdprofessorOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperiorListNewAvaliacaodocentesuperior);
                        oldIdprofessorOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior = em.merge(oldIdprofessorOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior);
                    }
                }
            }
            for (Autoavaliacao autoavaliacaoListOldAutoavaliacao : autoavaliacaoListOld) {
                if (!autoavaliacaoListNew.contains(autoavaliacaoListOldAutoavaliacao)) {
                    autoavaliacaoListOldAutoavaliacao.setIdprofessor(null);
                    autoavaliacaoListOldAutoavaliacao = em.merge(autoavaliacaoListOldAutoavaliacao);
                }
            }
            for (Autoavaliacao autoavaliacaoListNewAutoavaliacao : autoavaliacaoListNew) {
                if (!autoavaliacaoListOld.contains(autoavaliacaoListNewAutoavaliacao)) {
                    Professor oldIdprofessorOfAutoavaliacaoListNewAutoavaliacao = autoavaliacaoListNewAutoavaliacao.getIdprofessor();
                    autoavaliacaoListNewAutoavaliacao.setIdprofessor(professor);
                    autoavaliacaoListNewAutoavaliacao = em.merge(autoavaliacaoListNewAutoavaliacao);
                    if (oldIdprofessorOfAutoavaliacaoListNewAutoavaliacao != null && !oldIdprofessorOfAutoavaliacaoListNewAutoavaliacao.equals(professor)) {
                        oldIdprofessorOfAutoavaliacaoListNewAutoavaliacao.getAutoavaliacaoList().remove(autoavaliacaoListNewAutoavaliacao);
                        oldIdprofessorOfAutoavaliacaoListNewAutoavaliacao = em.merge(oldIdprofessorOfAutoavaliacaoListNewAutoavaliacao);
                    }
                }
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListOldAvaliacaodocenteestudante : avaliacaodocenteestudanteListOld) {
                if (!avaliacaodocenteestudanteListNew.contains(avaliacaodocenteestudanteListOldAvaliacaodocenteestudante)) {
                    avaliacaodocenteestudanteListOldAvaliacaodocenteestudante.setIdprofessor(null);
                    avaliacaodocenteestudanteListOldAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListOldAvaliacaodocenteestudante);
                }
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListNewAvaliacaodocenteestudante : avaliacaodocenteestudanteListNew) {
                if (!avaliacaodocenteestudanteListOld.contains(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante)) {
                    Professor oldIdprofessorOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante = avaliacaodocenteestudanteListNewAvaliacaodocenteestudante.getIdprofessor();
                    avaliacaodocenteestudanteListNewAvaliacaodocenteestudante.setIdprofessor(professor);
                    avaliacaodocenteestudanteListNewAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                    if (oldIdprofessorOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante != null && !oldIdprofessorOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante.equals(professor)) {
                        oldIdprofessorOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                        oldIdprofessorOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante = em.merge(oldIdprofessorOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                    }
                }
            }
            for (Disciplina disciplinaListOldDisciplina : disciplinaListOld) {
                if (!disciplinaListNew.contains(disciplinaListOldDisciplina)) {
                    disciplinaListOldDisciplina.setIdprofessor(null);
                    disciplinaListOldDisciplina = em.merge(disciplinaListOldDisciplina);
                }
            }
            for (Disciplina disciplinaListNewDisciplina : disciplinaListNew) {
                if (!disciplinaListOld.contains(disciplinaListNewDisciplina)) {
                    Professor oldIdprofessorOfDisciplinaListNewDisciplina = disciplinaListNewDisciplina.getIdprofessor();
                    disciplinaListNewDisciplina.setIdprofessor(professor);
                    disciplinaListNewDisciplina = em.merge(disciplinaListNewDisciplina);
                    if (oldIdprofessorOfDisciplinaListNewDisciplina != null && !oldIdprofessorOfDisciplinaListNewDisciplina.equals(professor)) {
                        oldIdprofessorOfDisciplinaListNewDisciplina.getDisciplinaList().remove(disciplinaListNewDisciplina);
                        oldIdprofessorOfDisciplinaListNewDisciplina = em.merge(oldIdprofessorOfDisciplinaListNewDisciplina);
                    }
                }
            }
            for (Users usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    usersListOldUsers.setIdprofessor(null);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (Users usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    Professor oldIdprofessorOfUsersListNewUsers = usersListNewUsers.getIdprofessor();
                    usersListNewUsers.setIdprofessor(professor);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldIdprofessorOfUsersListNewUsers != null && !oldIdprofessorOfUsersListNewUsers.equals(professor)) {
                        oldIdprofessorOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldIdprofessorOfUsersListNewUsers = em.merge(oldIdprofessorOfUsersListNewUsers);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = professor.getIdprofessor();
                if (findProfessor(id) == null) {
                    throw new NonexistentEntityException("The professor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professor professor;
            try {
                professor = em.getReference(Professor.class, id);
                professor.getIdprofessor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The professor with id " + id + " no longer exists.", enfe);
            }
            Cargochefia idcargochefia = professor.getIdcargochefia();
            if (idcargochefia != null) {
                idcargochefia.getProfessorList().remove(professor);
                idcargochefia = em.merge(idcargochefia);
            }
            Categoriaa idcategoria = professor.getIdcategoria();
            if (idcategoria != null) {
                idcategoria.getProfessorList().remove(professor);
                idcategoria = em.merge(idcategoria);
            }
            Departamento iddepartamento = professor.getIddepartamento();
            if (iddepartamento != null) {
                iddepartamento.getProfessorList().remove(professor);
                iddepartamento = em.merge(iddepartamento);
            }
            Regime idregime = professor.getIdregime();
            if (idregime != null) {
                idregime.getProfessorList().remove(professor);
                idregime = em.merge(idregime);
            }
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList = professor.getAvaliacaodocentesuperiorList();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperior : avaliacaodocentesuperiorList) {
                avaliacaodocentesuperiorListAvaliacaodocentesuperior.setIdprofessor(null);
                avaliacaodocentesuperiorListAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListAvaliacaodocentesuperior);
            }
            List<Autoavaliacao> autoavaliacaoList = professor.getAutoavaliacaoList();
            for (Autoavaliacao autoavaliacaoListAutoavaliacao : autoavaliacaoList) {
                autoavaliacaoListAutoavaliacao.setIdprofessor(null);
                autoavaliacaoListAutoavaliacao = em.merge(autoavaliacaoListAutoavaliacao);
            }
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList = professor.getAvaliacaodocenteestudanteList();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudante : avaliacaodocenteestudanteList) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudante.setIdprofessor(null);
                avaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
            }
            List<Disciplina> disciplinaList = professor.getDisciplinaList();
            for (Disciplina disciplinaListDisciplina : disciplinaList) {
                disciplinaListDisciplina.setIdprofessor(null);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
            }
            List<Users> usersList = professor.getUsersList();
            for (Users usersListUsers : usersList) {
                usersListUsers.setIdprofessor(null);
                usersListUsers = em.merge(usersListUsers);
            }
            em.remove(professor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Professor> findProfessorEntities() {
        return findProfessorEntities(true, -1, -1);
    }

    public List<Professor> findProfessorEntities(int maxResults, int firstResult) {
        return findProfessorEntities(false, maxResults, firstResult);
    }

    private List<Professor> findProfessorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Professor.class));
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

    public Professor findProfessor(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Professor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfessorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Professor> rt = cq.from(Professor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
