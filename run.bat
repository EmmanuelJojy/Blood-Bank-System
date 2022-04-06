@echo off
echo. && (
    echo * Compiling to /build && javac -d build *.java && (
        echo * Setting up assets && >NUL copy assets build && (
            echo * Restoring Session && >NUL copy session build && (
                cd build && echo * Starting Execution at /build/Main.class && (
                    java Main && (
                        echo. && echo * Saving Session && >NUL copy *.ser ..\session && (                            
                            echo * Destroy Build && cd .. && del /Q build && echo.
                        )
                    )
                )
            )
        )
    )
)