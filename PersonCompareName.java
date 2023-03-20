import java.util.Comparator;
/**
 * The class implements Comparator<Person> and is used to compare last name of 2 person.
 * If last names are in same order it compare first names. Letter case is ignored.
 * It overrides compare method. The comparison is in ascending alphabet order (A-Ö).
 */
class PersonCompareName implements Comparator<Person> {
    /**
     * Compare names of two persons
     * @param p1 person 1
     * @param p2 person 2
     * @return positive int if (p1 > p2), zero if (p1 equal p2), negative int if (p1 < p2)
     */
    @Override
    public int compare(Person p1, Person p2) {
        if(p1.getLastName().compareToIgnoreCase(p2.getLastName())==0)
            return p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
        else
            return p1.getLastName().compareToIgnoreCase(p2.getLastName());
    }
}