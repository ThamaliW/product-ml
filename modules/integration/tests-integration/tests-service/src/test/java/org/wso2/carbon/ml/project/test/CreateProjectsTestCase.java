/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.ml.project.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.ml.integration.common.utils.MLIntegrationBaseTest;
import org.wso2.carbon.ml.integration.common.utils.MLIntegrationTestConstants;
import org.wso2.carbon.ml.integration.common.utils.exception.MLIntegrationBaseTestException;

public class CreateProjectsTestCase extends MLIntegrationBaseTest {
    
    private static String DatasetName = "SampleDataForCreateProjectTestCase";

    @BeforeClass(alwaysRun = true, groups = "wso2.ml.integration")
    public void initTest() throws Exception {
        super.init();
        uploadDatasetFromCSV(DatasetName, "1.0", "data/fcSample.csv");
    }

    /**
     * Test creating a project.
     * 
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     * @throws MLIntegrationBaseTestException 
     */
    @Test(groups = "wso2.ml.integration", description = "Create a project")
    public void testCreateProject() throws ClientProtocolException, IOException, URISyntaxException, 
            MLIntegrationBaseTestException {
        CloseableHttpResponse response = createProject("TestProjectForCreatProjectTestCase", DatasetName);
        Assert.assertEquals(MLIntegrationTestConstants.HTTP_OK, response.getStatusLine().getStatusCode());
        response.close();
    }
    
    /**
     * Test creating a project with a duplicate project name.
     * 
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     * @throws MLIntegrationBaseTestException 
     */
    //FIXME: This should fail!!
    @Test(groups = "wso2.ml.integration", description = "Create a project with duplicate Name", dependsOnMethods = 
            "testCreateProject")
    public void testCreateProjectWithDuplicateName() throws ClientProtocolException, IOException, URISyntaxException,
            MLIntegrationBaseTestException {
        CloseableHttpResponse response = createProject("TestProjectForCreatProjectTestCase", DatasetName);
        Assert.assertEquals(MLIntegrationTestConstants.HTTP_OK, response.getStatusLine().getStatusCode());
        response.close();
    }
    
    /**
     * Test creating a project without the project name.
     * 
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     * @throws MLIntegrationBaseTestException 
     */
    @Test(groups = "wso2.ml.integration", description = "Create a project without name", dependsOnMethods = 
            "testCreateProject")
    public void testCreateProjectWithoutName() throws ClientProtocolException, IOException, URISyntaxException,
            MLIntegrationBaseTestException {
        CloseableHttpResponse response = createProject(null, DatasetName);
        Assert.assertEquals(MLIntegrationTestConstants.HTTP_BAD_REQUEST, response.getStatusLine().getStatusCode());
        response.close();
    }
    
    /**
     * Test creating a project without the project name.
     * 
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     * @throws MLIntegrationBaseTestException
     */
    @Test(groups = "wso2.ml.integration", description = "Create a project without a dataset")
    public void testCreateProjectWithoutDataset() throws ClientProtocolException, IOException, URISyntaxException,
            MLIntegrationBaseTestException {
        CloseableHttpResponse response = createProject("TestProjectForCreatProjectTestCase-2", null);
        Assert.assertEquals(MLIntegrationTestConstants.HTTP_BAD_REQUEST, response.getStatusLine()
                .getStatusCode());
        response.close();
    }
}