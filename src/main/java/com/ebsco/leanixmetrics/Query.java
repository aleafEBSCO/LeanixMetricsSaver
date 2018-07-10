package com.ebsco.leanixmetrics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import net.leanix.api.GraphqlApi;
import net.leanix.api.common.ApiClient;
import net.leanix.api.common.ApiException;
import net.leanix.api.models.GraphQLRequest;
import net.leanix.api.models.GraphQLResult;



public class Query {
	
	public String fileToString(String filename) {
		String line;
		StringBuilder builder = new StringBuilder();
		InputStream in = getClass().getResourceAsStream(filename);
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			while((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Unable to open file '" + filename + "'");
		}
		catch(IOException e) {
			System.out.println("Error reading file '" + filename + "'");
		}
		
		return builder.toString();
	}
	
	public Map<String, Map<String, Object>> getInfo(ApiClient apiClient, String graphQLQuery) {
		String query = fileToString(graphQLQuery);
		GraphqlApi graphqlApi = new GraphqlApi(apiClient);
		GraphQLRequest request = new GraphQLRequest();
		request.setQuery(query);
		GraphQLResult result = new GraphQLResult();
		
		Map<String, Map<String, Object>> retData = new LinkedHashMap<String, Map<String, Object>>();
		//System.out.println("before");
		try {
			result = graphqlApi.processGraphQL(request);
			retData = (LinkedHashMap<String, Map<String, Object>>) result.getData();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("after");
		//return result.toString();
		System.out.println(result.getData().getClass());
		//return result.getData().toString();
		return retData;
	}

}
