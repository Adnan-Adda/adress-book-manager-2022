import java.util.Comparator;
/**
 * The class implements Comparator<Person> and is used to compare signature of 2 person.
 * It overrides compare method. The comparison is in ascending alphabet order (A-Ö).
 */
class PersonCompareSignature implements Comparator<Person> {
    /**
     * Compare signature of two persons
     * @param p1 person 1
     * @param p2 person 2
     * @return positive int if (p1 > p2), zero if (p1 equal p2), negative int if (p1 < p2)
     */
    @Override
    public int compare(Person p1, Person p2){
        return p1.getSignature().compareTo(p2.getSignature());
    }
}