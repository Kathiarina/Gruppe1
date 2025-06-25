package at.fhburgenland;

public class Main {


    public static void main(String[] args) {
        RennverwaltungDriver rennverwaltung = new RennverwaltungDriver();
        rennverwaltung.rennverwaltungStart();

        //Rennstrecke rennstrecke = Rennstrecke.rennstreckeAuswaehlen();
        //rennstrecke.rennstreckeHinzufuegen("Spielefeld", "Steiermark");
        //rennstrecke.rennstreckeLoeschen(4);
        /*rennstrecke.alleRennstreckenAnzeigen();
        if (rennstrecke != null) {
            Rennen.rennenHinzufuegen(LocalDateTime.now(), rennstrecke);
            Rennen.alleRennenAnzeigen();
        } else {
            System.err.println("Rennen konnte nicht erstellt werden.");
        }
        Rennen.rennenLoeschen(3);
        Rennen.alleRennenAnzeigen();*/

        /*Hauptsponsor hauptsponsor = Hauptsponsor.hauptsponsorAuswaehlen();
        hauptsponsor.hauptsponsorHinzufuegen("ONYX", 50000000);
        hauptsponsor.alleHauptsponsorenAnzeigen();
        Nationalitaet nationalitaet = Nationalitaet.nationalitaetAuswaehlen();
        nationalitaet.nationalitaetHinzufuegen("Österreich");
        nationalitaet.alleNationalitaetenAnzeigen();
        Team team = new Team();
        if (nationalitaet != null && hauptsponsor != null) {
            team.teamHinzufuegen("Ferrari", 1993, nationalitaet, hauptsponsor);
            team.alleTeamsAnzeigen();
        } else {
            System.err.println("Team konnte nicht erstellt werden.");
        }*/

       /* Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.fahrzeugtypHinzufuegen("McLaren", "Turbomotor", 789);
        fahrzeugtyp.alleFahrzeugtypenAnzeigen();*/
/*
        Fahrzeugtyp fahrzeugtyp = FahrzeugtypService.fahrzeugtypAuswaehlen();
        Team team = TeamService.teamAuswaehlen();
        if (fahrzeugtyp != null && team != null) {
            FahrzeugService.fahrzeugHinzufuegen(fahrzeugtyp, team);
            FahrzeugService.alleFahrzeugeAnzeigen();
        } else {
            System.err.println("Fahrzeug konnte nicht erstellt werden.");
        }

        Nationalitaet nationalitaet = NationalitaetService.nationalitaetAuswaehlen();
        Fahrzeug fahrzeug = FahrzeugService.fahrzeugAuswaehlen();
        if (nationalitaet != null && fahrzeug != null) {
            FahrerService.fahrerHinzufuegen("Louis", "Hamilton", nationalitaet, fahrzeug);
            FahrerService.alleFahrerAnzeigen();
        } else {
            System.err.println("Fahrer konnte nicht erstellt werden.");
        }*/
    }







/*
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
            */

}




