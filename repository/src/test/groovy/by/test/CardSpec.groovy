package by.test

import spock.lang.Specification

import com.by.model.Card
import com.by.model.CardRule
import com.by.typeEnum.ValidEnum


class CardSpec extends Specification {
	def setupSpec(){
		
	}

	def "getMaxScore Test"(){
		given: "两个规则，在时间范围内的规则1，长期有效规则2"
		def beginTime=Calendar.getInstance();
		def endTime=Calendar.getInstance();
		beginTime.set(2016,1,1)
		endTime.set(2016,12,31)

		def rule1=new CardRule()
		rule1.with {
			setValid(ValidEnum.VALID)
			setScore(10)
			setBeginTime(beginTime)
			setEndTime(endTime)
		}
		def rule2=new CardRule()
		rule2.with{
			setValid(ValidEnum.VALID)
			setScore(5)
		}
		def rules=[rule1, rule2]

		expect: "采用规则2的规则"
		Card.getMaxScore(rules)==10;
	}
}
