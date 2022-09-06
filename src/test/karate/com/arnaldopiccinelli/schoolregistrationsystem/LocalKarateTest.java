package com.arnaldopiccinelli.schoolregistrationsystem;

import com.intuit.karate.junit5.Karate;

class LocalKarateTest {
    @Karate.Test
    Karate testAll() {
        return Karate.run().relativeTo(getClass()).tags("~@ignore");
    }
}
