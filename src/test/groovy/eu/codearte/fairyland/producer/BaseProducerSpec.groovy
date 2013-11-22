/*
 * Copyright (c) 2013. Codearte
 */

package eu.codearte.fairyland.producer

import spock.lang.Specification
import spock.lang.Unroll

import java.util.regex.Pattern

class BaseProducerSpec extends Specification {

	def baseProducer = Spy(BaseProducer);

	def setup() {
		baseProducer.randomBetween('0', '9') >> '7'
		baseProducer.randomBetween('a', 'z') >> 'x'
	}

	def "should replace # with digit 7"() {
		expect:
		baseProducer.numerify("Tes#t#") == "Tes7t7"
	}

	def "should replace ? with letter x"() {
		expect:
		baseProducer.letterify("Tes?t?") == "Tesxtx"
	}

	def "should replace # and ? with 7 and x respectively"() {
		expect:
		baseProducer.bothify("Test?#") == "Testx7"
	}

	def "should replace ? with letter from desired range"() {
		when:
		def result = baseProducer.letterify("Test??", 'A' as char, 'A' as char)
		then:
		result == "TestAA"
	}

	@Unroll
	def "should generate random number from given range #from - #to"() {
		setup:
		def randomGenerator = new BaseProducer();

		expect:
		def between = randomGenerator.randomBetween(from, to)

		between >= from
		between <= to

		where:
		from | to
		5    | 9
		1    | 2
		1    | 3
		0    | 4
		48   | 57
		2L   | 2L
		-5L  | -2L
		-3L  | 2L
		2.0  | 3.0
		-2.0 | -1.0
	}
}
