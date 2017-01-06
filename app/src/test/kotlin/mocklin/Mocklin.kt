package mocklin

import org.mockito.InOrder
import org.mockito.MockSettings
import org.mockito.MockingDetails
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.mockito.stubbing.OngoingStubbing
import org.mockito.stubbing.Stubber
import org.mockito.verification.VerificationMode
import org.mockito.verification.VerificationWithTimeout
import kotlin.reflect.KClass

/**
 *
 */
object Mocklin {

    fun atLeast(numInvocations: Int): VerificationMode = Mockito.atLeast(numInvocations)!!

    fun atLeastOnce(): VerificationMode = Mockito.atLeastOnce()!!
    fun atMost(maxNumberOfInvocations: Int): VerificationMode = Mockito.atMost(maxNumberOfInvocations)!!
    fun calls(wantedNumberOfInvocations: Int): VerificationMode = Mockito.calls(wantedNumberOfInvocations)!!

    fun <T> doAnswer(answer: (InvocationOnMock) -> T?): Stubber = Mockito.doAnswer { answer(it) }!!

    fun doCallRealMethod(): Stubber = Mockito.doCallRealMethod()!!
    fun doNothing(): Stubber = Mockito.doNothing()!!
    fun doReturn(value: Any?): Stubber = Mockito.doReturn(value)!!
    fun doThrow(toBeThrown: KClass<out Throwable>): Stubber = Mockito.doThrow(toBeThrown.java)!!

    fun <T> eq(value: T): T = Mockito.eq(value) ?: value
    fun ignoreStubs(vararg mocks: Any): Array<out Any> = Mockito.ignoreStubs(*mocks)!!
    fun inOrder(vararg mocks: Any): InOrder = Mockito.inOrder(*mocks)!!
    fun inOrder(vararg mocks: Any, evaluation: InOrder.() -> Unit) = Mockito.inOrder(*mocks).evaluation()

    fun <T : Any> isNotNull(): T? = Mockito.isNotNull() as T
    fun <T : Any> isNull(): T? = Mockito.isNull() as T

    inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)!!
    inline fun <reified T : Any> mock(defaultAnswer: Answer<Any>): T = Mockito.mock(T::class.java, defaultAnswer)!!
    inline fun <reified T : Any> mock(s: MockSettings): T = Mockito.mock(T::class.java, s)!!
    inline fun <reified T : Any> mock(s: String): T = Mockito.mock(T::class.java, s)!!


    infix fun <T> OngoingStubbing<T>.doReturn(t: T): OngoingStubbing<T> = thenReturn(t)
    fun <T> OngoingStubbing<T>.doReturn(t: T, vararg ts: T): OngoingStubbing<T> = thenReturn(t, *ts)
    inline infix fun <reified T> OngoingStubbing<T>.doReturn(ts: List<T>): OngoingStubbing<T> = thenReturn(ts[0], *ts.drop(1).toTypedArray())

    infix fun <T> OngoingStubbing<T>.doThrow(t: Throwable): OngoingStubbing<T> = thenThrow(t)
    fun <T> OngoingStubbing<T>.doThrow(t: Throwable, vararg ts: Throwable): OngoingStubbing<T> = thenThrow(t, *ts)
    infix fun <T> OngoingStubbing<T>.doThrow(t: KClass<out Throwable>): OngoingStubbing<T> = thenThrow(t.java)
    fun <T> OngoingStubbing<T>.doThrow(t: KClass<out Throwable>, vararg ts: KClass<out Throwable>): OngoingStubbing<T> = thenThrow(t.java, *ts.map { it.java }.toTypedArray())

    fun mockingDetails(toInspect: Any): MockingDetails = Mockito.mockingDetails(toInspect)!!
    fun never(): VerificationMode = Mockito.never()!!

    fun only(): VerificationMode = Mockito.only()!!
    fun <T> refEq(value: T, vararg excludeFields: String): T? = Mockito.refEq(value, *excludeFields)

    fun <T> reset(vararg mocks: T) = Mockito.reset(*mocks)

    fun <T> same(value: T): T = Mockito.same(value) ?: value

    inline fun <reified T : Any> spy(): T = Mockito.spy(T::class.java)!!
    fun <T> spy(value: T): T = Mockito.spy(value)!!

    fun timeout(millis: Long): VerificationWithTimeout = Mockito.timeout(millis)!!
    fun times(numInvocations: Int): VerificationMode = Mockito.times(numInvocations)!!
    fun validateMockitoUsage() = Mockito.validateMockitoUsage()

    fun <T> verify(mock: T): T = Mockito.verify(mock)!!
    fun <T> verify(mock: T, mode: VerificationMode): T = Mockito.verify(mock, mode)!!
    fun <T> verifyNoMoreInteractions(vararg mocks: T) = Mockito.verifyNoMoreInteractions(*mocks)
    fun verifyZeroInteractions(vararg mocks: Any) = Mockito.verifyZeroInteractions(*mocks)

    fun <T> whenever(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)!!
    fun withSettings(): MockSettings = Mockito.withSettings()!!
}
