package com.ing.android.coolusers.domain.parser

import com.ing.android.coolusers.domain.listeners.UserListResult
import com.ing.android.coolusers.domain.listeners.UserResult

typealias UserParserFunc = (String) -> UserResult
typealias UserListParserFunc = (String) -> UserListResult

class DataParser {

    inline fun parseUser(payload: String, func: UserParserFunc): UserResult =
            func(payload)

    inline fun parseUserList(payload: String, func: UserListParserFunc): UserListResult =
            func(payload)
}
