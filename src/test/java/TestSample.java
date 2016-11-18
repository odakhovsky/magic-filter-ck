import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import org.junit.runner.RunWith;

import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class TestSample {
    private String testCase = "testCase";
    private String emptyString = "";

    {
        describe("String.length()", () -> {
            describe("When empty string", () -> {
                it("should return 0", () -> {
                    expect(0).toEqual(emptyString.length());
                });
            });

            describe("When not empty string", () -> {
                it("should return properly length", () -> {
                    expect(8).toEqual(testCase.length());
                });
            });
        });
    }
}