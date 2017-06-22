package bio.knowledge.server.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;

import bio.knowledge.client.ApiClient;

public class GenericKnowledgeService {
	
	@Value("${aggregator.url}")
	String aggregatorBasePath;

	protected <T> CompletableFuture<List<T>> query(SupplierBuilder<T> builder) {
		
		ApiClient apiClient = new ApiClient();
		apiClient.setBasePath(aggregatorBasePath);
		ListSupplier<T> supplier = builder.build(apiClient);
		
		return CompletableFuture.supplyAsync(supplier).exceptionally((error) -> {
			return new ArrayList<>();
		});
	}

	/**
	 * Wraps {@code wraps Supplier<List<T>>}, used for the sake of generic
	 * queries in {@code GenericKnowledgeService}. The {@code get()} method
	 * <b>must</b> return a List. It may not return {@code null} or throw an exception
	 * (so that nothing is returned). The list that it returns is concatenated
	 * with the lists returned by other suppliers, and so if there is no data to
	 * return simply return an empty list.
	 * 
	 * @author Lance Hannestad
	 *
	 * @param <T>
	 */
	public abstract class ListSupplier<T> implements Supplier<List<T>> {
		
		/**
		 * The {@code get()} method <b>must</b> return a List, otherwise the
		 * CompletableFuture combining in {@code combineFutures()}. will not
		 * work. To ensure that get() will never return null, I have wrapped it
		 * another method that will be overridden by extended classes. Now even
		 * if the author of those extended classes makes a mistake and allows
		 * for {@code null} to be returned or exceptions be thrown, it should
		 * get caught here and not harm the combining of completable futures.
		 */
		@Override
		public List<T> get() {
			try {
				List<T> result = getList();
				if (result != null) {
					return result;
				} else {
					return new ArrayList<T>();
				}
			} catch (Exception e) {
				return new ArrayList<T>();
			}
		}
		
		public abstract List<T> getList();
	}
	
	/**
	 * A class that builds custom ListSupplier objects, for the use of
	 * generating CompletableFutures within {@code GenericKnowledgeService.query()}.
	 * 
	 * @author Lance Hannestad
	 *
	 * @param <T>
	 */
	public abstract class SupplierBuilder<T> {
		public abstract ListSupplier<T> build(ApiClient apiClient);
	}
}

