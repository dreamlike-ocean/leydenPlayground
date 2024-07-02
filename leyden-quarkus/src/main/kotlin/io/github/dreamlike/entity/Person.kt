package io.github.dreamlike.entity

import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class Person(val name: String, val age: Int)
