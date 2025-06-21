package at.fhburgenland;

import jakarta.persistence.*;
import tables.Rennen;
import tables.Rennstrecke;

import java.time.LocalDateTime;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        System.out.println("tables2");
        Rennstrecke rennstrecke = Rennstrecke.rennstreckeAuswaehlen();
        //rennstrecke.rennstreckeHinzufuegen("Spielefeld", "Steiermark");
        rennstrecke.rennstreckeLoeschen(4);
        rennstrecke.alleRennstreckenAnzeigen();
        if (rennstrecke != null) {
            Rennen.rennenHinzufuegen(LocalDateTime.now(), rennstrecke);
            Rennen.alleRennenAnzeigen();
        } else {
            System.err.println("Rennen konnte nicht erstellt werden.");
        }
    }
/*
    public static void addPerson(String vorname, String nachname, int gehalt){
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try{
            et = em.getTransaction();
            et.begin();

            Person p = new Person(vorname, nachname, gehalt);
            em.persist(p);
            et.commit();
            System.out.println("Person erfolgreich angelegt.");
        } catch (Exception e) {
            if(et!=null){
                et.rollback();
                System.out.println(e.getMessage());
            }
        } finally {
            em.close();
        }
    }

    public static void printAllPersons(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT p FROM Person p";
        TypedQuery<Person> tq = em.createQuery(query, Person.class);

        List<Person> personList = null;

        try{
            personList = tq.getResultList();
            for(Person person : personList){
                System.out.println("Nr: " + person.getPnr() + ", Vorname " + person.getVorname() + ", Nachname " + person.getNachname() + ", Gehalt: " + person.getGehalt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void printSinglePerson(int pnr){
        EntityManager em = EMF.createEntityManager();
        Person person = null;
        try{
            person = em.find(Person.class, pnr);
                System.out.println("Read Person Nr: " + person.getPnr() + ", Vorname " + person.getVorname() + ", Nachname " + person.getNachname() + ", Gehalt: " + person.getGehalt());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void readPerson(String vorname){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT p FROM Person p WHERE p.vorname = :vname";
        TypedQuery<Person> tq = em.createQuery(query, Person.class);
        tq.setParameter("vname", vorname);

        List<Person> personList = null;

        try{
            personList = tq.getResultList();
            for(Person person : personList){
                System.out.println("Read Person Nr: " + person.getPnr() + ", Vorname " + person.getVorname() + ", Nachname " + person.getNachname() + ", Gehalt: " + person.getGehalt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    public static void updatePerson(int pnr, String vorname, String nachname, int gehalt) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Person p = null;

        try {
            et = em.getTransaction();
            et.begin();
            p = em.find(Person.class, pnr);
            p.setVorname(vorname);
            p.setNachname(nachname);
            p.setGehalt(gehalt);


            em.persist(p);
            et.commit();
            System.out.println("Person erfolgreich upgedated.");
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println(e.getMessage());
            }
        } finally {
            em.close();
        }
    }
        public static void deletePerson(int pnr){
            EntityManager em = EMF.createEntityManager();
            EntityTransaction et = null;
            Person p = null;

            try{
                et = em.getTransaction();
                et.begin();
                p = em.find(Person.class, pnr);
                em.remove(p);
                et.commit();
                System.out.println("Person erfolgreich gelöscht.");
            } catch (Exception e) {
                if(et!=null){
                    et.rollback();
                    System.out.println(e.getMessage());
                }
            } finally {
                em.close();
            }
        }*/

}




