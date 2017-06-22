package bio.knowledge.server.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.google.gson.JsonSyntaxException;

import bio.knowledge.client.ApiClient;
import bio.knowledge.client.api.ExactmatchesApi;

@Service
public class KnowledgeBeaconService extends GenericKnowledgeService {

	public CompletableFuture<List<String>> getExactMatchesToConcept(String conceptId) {
		SupplierBuilder<String> builder = new SupplierBuilder<String>() {

			@Override
			public ListSupplier<String> build(ApiClient apiClient) {
				return new ListSupplier<String>() {

					@Override
					public List<String> getList() {
						
						ExactmatchesApi exactmatchesApi = new ExactmatchesApi(apiClient);
						
						try {
							return exactmatchesApi.getExactMatchesToConcept(conceptId);
							
						} catch (Exception e) {
							printError(apiClient, e);
							return new ArrayList<>();
						}
					}
					
				};
			}
			
		};
		return query(builder);
	}
	
	private void printError(ApiClient apiClient, Exception e) {
		System.err.println("Error Querying:   " + apiClient.getBasePath());
		System.err.println("Error message:    " + e.getMessage());
		if (e instanceof JsonSyntaxException) {
			System.err.println("PROBLEM WITH DESERIALIZING SERVER RESPONSE");
		}
	}
	
}
