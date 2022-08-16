package com.order.api

import spock.lang.Specification

class SampleSpec extends Specification {

    Sample target

    def setup() {
        target = new Sample()
    }

    def "getFullName"() {
        given:
        def lastName = "テスト"
        def firstName = "太郎"

        when:
        def actual = target.getFullName(lastName, firstName)

        then:
        actual == "テスト太郎"
    }
}
