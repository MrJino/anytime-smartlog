package no.jinil.utils.smartlog

import no.jinil.utils.smartlog.platform.SpringLog

class SmartLog(
    exception: (String) -> Unit,
    lifecycle: (String) -> Unit,
    debug: (String) -> Unit,
    verbose: (String) -> Unit,
) {
    val spring = SpringLog(
        exception = exception,
        lifecycle = lifecycle,
        debug = debug,
        verbose = verbose,
    )
}