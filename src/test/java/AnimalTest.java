import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;


public class AnimalTest  {

    @Test
    public void animal_has_height()  {
        Animal sut = new Animal();
        Assert.assertEquals(10, sut.height());
    }


}
