import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

public class OtherStepDefs extends Steps {

    @Then("result list size is $size")
    public void checkResultListSize(@Named("size") int size) {
    }
}
