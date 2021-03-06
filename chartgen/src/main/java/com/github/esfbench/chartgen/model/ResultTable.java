package com.github.esfbench.chartgen.model;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ResultTable {
	private Map<String,FrameworkSummary> frameworks;

	public ResultTable(Set<Benchmark> benchmarks) {
		frameworks = new TreeMap<>();
		
		for (Benchmark benchmark : benchmarks) {
			transpose(benchmark, frameworkSummary(benchmark));
		}
	}
	
	private static void transpose(Benchmark benchmark, FrameworkSummary summary) {
		switch (benchmark.type) {
			case BASELINE:
				summary.baseline = benchmark.score;
				break;
			case INSERT_REMOVE:
				summary.insertRemove = benchmark.score;
				break;
			case PACKED:
				summary.packed = benchmark.score;
				break;
			case PLAIN:
				summary.plain = benchmark.score;
				break;
			case POOLED:
				summary.pooled = benchmark.score;
				break;
			default:
				throw new RuntimeException("missing case: " + benchmark.type);
			
		}
	}

	private FrameworkSummary frameworkSummary(Benchmark benchmark) {
		FrameworkSummary summary = frameworks.get(benchmark.framework);
		if (summary == null) {
			summary = new FrameworkSummary();
			summary.framework = benchmark.framework;
			frameworks.put(benchmark.framework, summary);
		}
		
		return summary;
	}

	public void printTable(PrintStream out) {
		out.println("| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove |");
		out.println("|------------------------|-----------|-----------|-----------|-----------|---------------|");
		for (FrameworkSummary summary : frameworks.values()) {
			String row = String.format(
				"| %-22s | %9.2f | %9.2f | %9.2f | %9.2f | %13.2f |",
					summary.framework,
					summary.baseline,
					summary.plain,
					summary.pooled,
					summary.packed,
					summary.insertRemove);
			
			out.println(row.replaceAll("0\\.00", "    "));
		}
		
	}

	private static class FrameworkSummary {
		public String framework;
		public float baseline;
		public float plain;
		public float pooled;
		public float packed;
		public float insertRemove;
		
		@Override
		public String toString() {
			return super.toString();
		}
	}
}
