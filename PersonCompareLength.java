import java.util.Comparator;

/**
 * The class implements Comparator<Person> and is used to compare length in cm of 2 person.
 * It overrides compare method. The comparison is in descending order (the biggest value first).
 */
class PersonCompareLength implements Comparator<Person> {
    /**
     * Compare length in cm of two person
     * @param p1 person 1
     * @param p2 person 2
     * @return positive int if (p2 > p1), zero if (p2 equal p1), negative int if (p2 < p1)
     */
    @Override
    public int compare(Person p1, Person p2){
        return p2.getLength() - p1.getLength();
    }
}