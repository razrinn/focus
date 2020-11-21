package id.ac.ui.cs.mobileprogramming.razrinn.focus.network.model

class Quote {
    var _id: String? = null
    var author: String? = null
    var content: String? = null

    override fun toString(): String {
        return "$author - $content"
    }
}