package com.digitalsamurai.math.tree.extensions

import java.lang.Exception

class NodeNotInTreeException : Exception() {
    override val message: String
        get() = "Parent node not in that tree!"
}