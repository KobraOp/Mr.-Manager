# Use Ubuntu-based OpenJDK image
FROM openjdk:17-jdk-slim

# Set environment variables
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH

# Install required packages
RUN apt-get update && apt-get install -y \
    wget unzip curl git && \
    mkdir -p $ANDROID_HOME/cmdline-tools && \
    cd $ANDROID_HOME/cmdline-tools && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip && \
    unzip commandlinetools-linux-10406996_latest.zip && \
    rm commandlinetools-linux-10406996_latest.zip && \
    mv cmdline-tools latest

# Accept licenses for SDK components
RUN yes | sdkmanager --sdk_root=$ANDROID_HOME --licenses

# Install necessary SDK components
RUN sdkmanager --sdk_root=$ANDROID_HOME "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# Set working directory
WORKDIR /app

# Copy the project files
WORKDIR /app
COPY . /app

# Grant execute permissions to Gradle wrapper

# Build the project
CMD ["./gradlew", "assembleDebug"]
