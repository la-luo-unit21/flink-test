package org.myorg.quickstart;

import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;


public class FraudDetectionJob {
	private static final double SMALL_AMOUNT = 1.00;
	private static final double LARGE_AMOUNT = 500.00;
	private static final Time THREE_MINUTE = Time.seconds(60 * 3);


	public static void main(String[] args) throws Exception {
		// Sets up the execution environment, which is the main entry point
		// to building Flink applications.
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//		Source mySource = new MySource(...);
//
//		DataStream<Event> stream = env.fromSource(
//				mySource,
//				WatermarkStrategy.noWatermarks(),
//				"MySourceName");

		Event[] transactions = {null, null};

		Pattern<Event, ?> pattern = Pattern.<Event>begin("rule#1").where(
				new SimpleCondition<Event>() {
					@Override
					public boolean filter(Event transaction) throws Exception {
						if (transaction.getAmount() < SMALL_AMOUNT) {
							transactions[0] = transaction;
							return true;
						}
						return false;
					}
				}
		).next("rule#2").where(
				new SimpleCondition<Event>() {
					@Override
					public boolean filter(Event transaction) throws Exception {

						if (transaction.getAmount() > LARGE_AMOUNT){
							transactions[1] = transaction;
							return true;
						}
						return false;
					}
				}
		).next("rule#3").where(
				new SimpleCondition<Event>() {
					@Override
					public boolean filter(Event transaction) throws Exception {

						return transaction.getAmount() < 0.3 * transactions[1].getAmount();
					}
				}
		).within(THREE_MINUTE).followedBy("rule#4").where(
				new SimpleCondition<Event>() {
					@Override
					public boolean filter(Event transaction) throws Exception {
						return transaction.getType().equals("payment");
					}
				}
		);

		env.execute("Fraud Detection..");
	}
}
