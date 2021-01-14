package com.ing.android.coolusers

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.listeners.UserResult
import com.ing.android.coolusers.domain.dto.User
import com.ing.android.coolusers.domain.usecases.*
import com.ing.android.coolusers.providers.data.gateways.local.GetUserAssetsCommandGateway
import com.ing.android.coolusers.utilities.InjectionUtils
import com.ing.android.coolusers.utilities.ServiceUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class DomainTest {

    @Test
    fun test_ParserSuccess() {
        val dataParser = InjectionUtils.provideDataParser()
        val userAsStr = RuntimeEnvironment.application.assets.
                open(ServiceUtils.buildGetUserUrl(SAMPLE_USER_ID) + ".json").bufferedReader().use { it.readText() }
        val userResult = dataParser.parseUser(userAsStr, ::parsePayload)

        assert((userResult is UserResult.UserSuccess),
                {"@test_ParserSuccess: unable to parse user properly"})

        assert((userResult as UserResult.UserSuccess).user.name.equals("Gandalf"),
                {"@test_ParserSuccess: unable to parse user properly"})

        assert(userResult.user.email.equals("gandie@gmail.com"),
                {"@test_ParserSuccess: unable to parse user properly"})
    }

    @Test
    fun test_ParserFailure() {
        val dataParser = InjectionUtils.provideDataParser()
        val userAsStr = RuntimeEnvironment.application.assets.
                open(ServiceUtils.buildGetUserUrl(MALFORMED_USER_ID) + ".json").bufferedReader().use { it.readText() }
        val userResult = dataParser.parseUser(userAsStr, ::parsePayload)

        assert((userResult is UserResult.UserFailure),
                {"@test_ParserFailure: unable to parse user properly"})

        assert((userResult as UserResult.UserFailure).reason.equals(SAMPLE_ERROR_MSG),
                {"@test_ParserFailure: unable to parse user properly"})
    }

    @Test
    fun test_GatewayPerformTransaction() {
        val sampleGateway = object : CommandGateway(RuntimeEnvironment.application) {
            override fun transact(arguments: Bundle, genericListener: GenericListener) {
                // no implementation needed
            }
        }
        val dummyCommand = object : Command {
            override fun execute(genericListener: GenericListener) {
                // no implementation needed
            }
        }
        val dummyListener = object : GenericListener {}
        sampleGateway.performTransaction(Bundle.EMPTY, dummyCommand, dummyListener)

    }

    private fun parsePayload(payload: String): UserResult {
        return try {
            val listType = object : TypeToken<User>() {}.type
            UserResult.UserSuccess(Gson().fromJson(payload, listType))
        } catch (exception: JsonSyntaxException) {
            Log.i(GetUserAssetsCommandGateway.TAG, exception.localizedMessage)
            UserResult.UserFailure(SAMPLE_ERROR_MSG)
        }
    }
}