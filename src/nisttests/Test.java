/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import results.Result;
import java.io.PrintWriter;

/**
 *
 * @author kenanince
 */
public interface Test {
	public Result[] runTest();
	public String getComputationalInformation();
	public String getDescription();
	public int getInputSizeRecommendation();
}
