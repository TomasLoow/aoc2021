package parserCombinators

typealias ParseResult<Token, Res> = Pair<Res, List<Token>>
typealias Parser<Token, Res> = (List<Token>) -> ParseResult<Token, Res>

class NoMatchException(message:String): Exception(message)

fun <Token, A,B,C> pCombineTwo(parser1 : Parser<Token, A>, parser2: Parser<Token, B>, combiner: (A, B) -> C): Parser<Token, C> {
    return { input ->
        val (a, rest1) = parser1(input)
        val (b, rest2) = parser2(rest1)
        Pair(combiner(a,b), rest2)
    }
}

fun <Token, A,B> pApplyFun(parser: Parser<Token, A>, f : (A) -> B): Parser<Token, B> {
    return { input ->
        val (a, rest) = parser(input)
        Pair(f(a), rest)
    }
}

fun <Token, A,B> pPair(parser1 : Parser<Token, A>, parser2: Parser<Token, B>): Parser<Token, Pair<A, B>> = pCombineTwo(parser1,parser2) { a, b -> Pair(a,b) }


fun <Token, Res> pList(parserList: List<Parser<Token,Res>>): Parser<Token, List<Res>> {
    return { input ->
        val res = mutableListOf<Res>()
        var leftOfInput = input
        for (parser in parserList) {
            val (elem, rest) = parser(leftOfInput)
            leftOfInput = rest
            res.add(elem)
        }
        Pair(res, leftOfInput.toList())
    }

}


fun <Token, A> pManySpecificCount(numChildren: Int, parser: Parser<Token, A>): Parser<Token, List<A>> {
    return { input ->
        var rest = input
        val res = (1..numChildren).map {
            val (a, rest1) = parser(rest)
            rest = rest1
            a
        }
        Pair(res, rest)
    }
}

fun <Token, A> pManySpecificTokenCount(numBytes: Int, parser: Parser<Token, A>): Parser<Token, List<A>> {
    return { input ->
        var consumedBytes = 0
        val res = mutableListOf<A>()
        while (consumedBytes < numBytes) {
            val (elem, rest) = parser(input.drop(consumedBytes))
            res.add(elem)
            consumedBytes = input.size - rest.size
        }
        Pair(res.toList(), input.drop(consumedBytes))
    }
}

fun <Token> pToken(token: Token): Parser<Token, Unit> {
    return { input ->
        if (input.isEmpty()) throw NoMatchException("Couldn't find literal $token in empty input")
        if (input.first() != token) throw NoMatchException("Couldn't find literal $token")
        Pair(Unit, input.drop(1))
    }
}


fun <Token> pLiteral(literal: List<Token>): Parser<Token, Unit> {
    return { input ->
        if (input.take(literal.size) != literal) {
            throw NoMatchException("Couldn't find literal $literal")
        } else {
            Pair(Unit, input.drop(literal.size))
        }
    }
}

infix fun <Token, A, B> (Parser<Token,A>).thenDo(parser: Parser<Token, B>): Parser<Token,B> {
    return {
            input ->
        val (_, r) = this(input)
        parser(r)
    }
}

infix fun <Token, A, B> (Parser<Token,A>).thenIgnore(parser: Parser<Token, B>): Parser<Token,A> {
    return {
            input ->
        val (a, r) = this(input)
        val (_, r2) = parser(r)
        Pair(a,r2)
    }
}

fun <Token, A>  pOneOf(parser: Parser<Token, A>, parser2: Parser<Token, A>): Parser<Token,A> {
    return { input ->
        try {
            val (a, rest) = parser(input)
            Pair(a, rest)
        } catch (e: NoMatchException) {
            val (a, rest) = parser2(input)
            Pair(a, rest)
        }
    }
}

fun pInt(input: List<Char>) : ParseResult<Char, Int> {
    val digits = input.takeWhile { it.isDigit() || it == '-'}
    if (digits.isEmpty()) throw NoMatchException("No integer found")

    return Pair(digits.joinToString("").toInt(), input.drop(digits.size))
}
