<body>

<h1 align="center"><strong>MeliApp</strong></h1>

<div align="center">
  <img src="https://github.com/user-attachments/assets/307c332b-4373-4804-9c2d-56fea67f214b" />
  <img src="https://github.com/user-attachments/assets/4d9f2fdd-68b8-4bd1-9191-13b98ffc89be" />
</div>

<br/>

<!-- Índice -->
<h2>Índice</h2>
<ol>
  <li><a href="#descripcion-general">Descripción General</a></li>
  <li><a href="#caracteristicas-principales">Características Principales</a></li>
  <li><a href="#arquitectura-estructura">Arquitectura y Estructura del Proyecto</a></li>
  <li><a href="#requerimientos">Requerimientos Técnicos y Prácticos</a></li>
  <li><a href="#tecnologias-librerias">Tecnologías y Librerías Utilizadas</a></li>
  <li>
    <a href="#ktlint">
      <img src="https://github.com/user-attachments/assets/4105c2b1-7907-44c1-bbe2-9aa7e25fab97" alt="New" style="width:32px; vertical-align:middle;" /> Ktlint
    </a>
  </li>
  <li>
    <a href="#tests-instrumentados">
      <img src="https://github.com/user-attachments/assets/4105c2b1-7907-44c1-bbe2-9aa7e25fab97" alt="New" style="width:32px; vertical-align:middle;" /> Tests Instrumentados
    </a>
  </li>
  <li>
    <a href="#github-actions">
      <img src="https://github.com/user-attachments/assets/4105c2b1-7907-44c1-bbe2-9aa7e25fab97" alt="New" style="width:32px; vertical-align:middle;" /> Integración con GitHub Actions
    </a>
  </li>    
  <li><a href="#firebase-crashlytics">Firebase Crashlytics</a></li>
  <li><a href="#pruebas-unitarias">Pruebas Unitarias</a></li>
  <li><a href="#ejecutar">Cómo Ejecutar el Proyecto</a></li>
  <li><a href="#download">Download</a></li>
  <li><a href="#capturas">Capturas de Pantalla</a></li>
  <li><a href="#contribuciones">Contribuciones</a></li>
  <li><a href="#licencia">Licencia</a></li>
</ol>

<hr/>

<!-- Descripción General -->
<h2 id="descripcion-general">Descripción General</h2>
<p>
  <strong>MeliApp</strong> es una aplicación móvil para Android que 
  consume las APIs públicas de 
  <a href="https://developers.mercadolibre.com.ar/es_ar/items-y-busquedas" target="_blank">Mercado Libre</a>. 
  Permite a los usuarios autenticarse (registro, inicio de sesión y restablecimiento de contraseña), 
  buscar productos mediante la barra de búsqueda integrada en la mayoría de pantallas y 
  visualizar tanto los resultados como el detalle de cada producto. 
</p>
<p>
  Esta app está desarrollada con <em>Kotlin</em>, <em>Jetpack Compose</em> y sigue una 
  arquitectura <em>MVI</em> (Model-View-Intent). Esto garantiza un código organizado, 
  escalable y fácilmente testeable, priorizando la experiencia de usuario y las buenas 
  prácticas de la plataforma Android.
</p>

<!-- Características Principales -->
<h2 id="caracteristicas-principales">Características Principales</h2>
<ul>
  <li><strong>Búsqueda Global</strong>: Barra de búsqueda en la parte superior (TopBar) 
    accesible en la mayoría de pantallas, mostrando resultados en tiempo real.</li>
  <li><strong>Pantalla de Resultados</strong>: Lista con datos relevantes (título, precio, 
    thumbnail, información de envío, etc.), con soporte para estados de carga, error y sin resultados.</li>
  <li><strong>Pantalla de Detalle</strong>: Muestra información detallada del producto seleccionado, 
    incluyendo precio, fotos, envío, cuotas, garantía, etc.</li>
  <li><strong>Pantallas de Autenticación</strong>: (Login, Registro y Restablecimiento de Contraseña) 
    con validaciones de campos y uso de Firebase para la gestión de usuarios.</li>
  <li><strong>Arquitectura MVI Consistente</strong>: Cada feature posee su propio ViewModel, 
    states (estados) e intents, aislando la lógica de negocio de la interfaz de usuario.</li>
  <li><strong>Flujo de Navegación Unificado</strong>: Gestión de rutas y pasos de navegación 
    mediante <em>Navigation Compose</em>.</li>
</ul>

<!-- Arquitectura y Estructura del Proyecto -->
<h2 id="arquitectura-estructura">Arquitectura y Estructura del Proyecto</h2>
<p>
  MeliApp se construyó bajo el patrón <strong>MVI</strong> (Model-View-Intent) 
  y una organización modular por <em>features</em>, manteniendo las capas 
  <strong>Data</strong>, <strong>Domain</strong> y <strong>Presentation</strong>.
</p>
<ul>
  <li><strong>Data Layer</strong>:
    <ul>
      <li>Data Sources: conexión con API de Mercado Libre, Firebase, etc.</li>
      <li>Repositorios: abstraen la lógica de acceso a datos y mapean los modelos 
        de API a modelos de dominio.</li>
    </ul>
  </li>
  <li><strong>Domain Layer</strong>:
    <ul>
      <li>Use Cases: encapsulan la lógica de negocio (e.g. <em>GetItemDetailUseCase</em>).</li>
      <li>Entities: modelos puros sin dependencias de infraestructuras externas.</li>
    </ul>
  </li>
  <li><strong>Presentation Layer</strong>:
    <ul>
      <li>ViewModels: contienen la lógica para gestionar estados y recibir intenciones de la UI.</li>
      <li>Composables (UI): representan la interfaz declarativa (Jetpack Compose).</li>
      <li>Componentes Reutilizables: SearchBar, campos de formularios, secciones de la UI, etc.</li>
    </ul>
  </li>
</ul>

<p>Estructura del proyecto:</p>
<pre>
meliapp/
├── core/
│   ├── components/
│   │   └── searchTopAppBar/
│   │       ├── components/
│   │       │   ├── CustomSearchTextField.kt
│   │       │   ├── LocationButton.kt
│   │       ├── SearchBarIntent.kt
│   │       ├── SearchBarState.kt
│   │       ├── SearchBarViewModel.kt
│   │       ├── SearchTopAppBar.kt
│   ├── utils/
│   │   ├── convertToHttps.kt
│   │   ├── toFormattedPrice.kt
│   ├── di/
│       └── globalModule.kt
├── features/
│   ├── authentication/
│   │   ├── data/
│   │   │   ├── datasource/
│   │   │   │   ├── AuthenticationDataSource.kt
│   │   │   │   ├── AuthenticationDataSourceImpl.kt
│   │   │   ├── repository/
│   │   │       ├── AuthRepository.kt
│   │   │       └── AuthRepositoryImpl.kt
│   │   ├── di/
│   │   │   └── authenticationModule.kt
│   │   ├── domain/
│   │   │   └── usecases/
│   │   │       ├── LoginUseCase.kt
│   │   │       ├── RegisterUseCase.kt
│   │   │       ├── ResetPasswordUseCase.kt
│   │   ├── presentation/
│   │       ├── components/
│   │       │   ├── ConfirmPasswordField.kt
│   │       │   ├── CustomActionButton.kt
│   │       │   ├── EmailInputField.kt
│   │       │   ├── GreetingMessage.kt
│   │       │   ├── NameInputField.kt
│   │       │   ├── NavigationLinkText.kt
│   │       │   ├── PasswordTextField.kt
│   │       │   ├── ResetPasswordNavigationButton.kt
│   │       ├── login/
│   │       │   ├── LoginIntent.kt
│   │       │   ├── LoginScreen.kt
│   │       │   ├── LoginState.kt
│   │       │   ├── LoginViewModel.kt
│   │       ├── register/
│   │       │   ├── RegisterIntent.kt
│   │       │   ├── RegisterScreen.kt
│   │       │   ├── RegisterState.kt
│   │       │   ├── RegisterViewModel.kt
│   │       ├── reset/
│   │           ├── ResetPasswordIntent.kt
│   │           ├── ResetPasswordScreen.kt
│   │           ├── ResetPasswordState.kt
│   │           ├── ResetPasswordViewModel.kt
│   ├── details/
│   │   ├── data/
│   │   │   ├── datasource/
│   │   │   │   ├── DetailsDataSource.kt
│   │   │   │   ├── DetailsDataSourceImpl.kt
│   │   │   ├── model/
│   │   │   │   └── ItemDetailResponse.kt
│   │   │   ├── repository/
│   │   │       ├── DetailsRepository.kt
│   │   │       └── DetailsRepositoryImpl.kt
│   │   ├── di/
│   │   │   └── detailsModule.kt
│   │   ├── domain/
│   │   │   └── usecases/
│   │   │       ├── GetItemDetailUseCase.kt
│   │   │       └── GetItemDetailUseCaseImpl.kt
│   │   ├── presentation/
│   │       ├── components/
│   │       │   ├── BuyOrSaveSection.kt
│   │       │   ├── HeaderSection.kt
│   │       │   ├── ImageCarousel.kt
│   │       │   ├── InstallmentsSection.kt
│   │       │   ├── ItemDetailContent.kt
│   │       │   ├── PriceSection.kt
│   │       │   ├── ShippingInfoSection.kt
│   │       │   ├── TitleSection.kt
│   │       │   ├── WarrantySection.kt
│   │       ├── DetailsScreen.kt
│   │       ├── DetailsState.kt
│   │       └── DetailsViewModel.kt
│   ├── home/
│   │   ├── data/
│   │   ├── domain/
│   │   ├── presentation/
│   │       ├── components/
│   │       │   ├── AdvertisementBanner.kt
│   │       │   ├── AdvertisementCarousel.kt
│   │       │   ├── CategoriesSection.kt
│   │       └── HomeScreen.kt
│   ├── results/
│   │   ├── data/
│   │   │   ├── datasource/
│   │   │   │   ├── ResultsDataSource.kt
│   │   │   │   ├── ResultsDataSourceImpl.kt
│   │   │   ├── model/
│   │   │   │   └── SearchResponse.kt
│   │   │   ├── repository/
│   │   │       ├── ResultsRepository.kt
│   │   │       └── ResultsRepositoryImpl.kt
│   │   ├── di/
│   │   │   └── searchModule.kt
│   │   ├── domain/
│   │   │   └── usecases/
│   │   │       ├── GetResultsUseCase.kt
│   │   │       └── GetResultsUseCaseImpl.kt
│   │   ├── presentation/
│   │       ├── components/
│   │       │   ├── ErrorView.kt
│   │       │   ├── LoadingIndicator.kt
│   │       │   ├── NoResultsView.kt
│   │       │   ├── ResultItem.kt
│   │       │   ├── ResultItemHorizontalDivider.kt
│   │       │   ├── ResultItemInstallmentsSection.kt
│   │       │   ├── ResultItemPriceSection.kt
│   │       │   ├── ResultItemProductDetails.kt
│   │       │   ├── ResultItemProductThumbnail.kt
│   │       │   ├── ResultItemProductTitle.kt
│   │       │   ├── ResultItemShippingSection.kt
│   │       │   ├── ResultsList.kt
│   │       ├── ResultsScreen.kt
│   │       ├── ResultsState.kt
│   │       └── ResultsViewModel.kt
├── navigation/
│   ├── AppNavigation.kt
│   └── Route.kt
└── ui/
    ├── MainActivity.kt
    └── MeliApp.kt
</pre>

<p>
  Cada <em>feature</em> posee subcarpetas para <em>data</em>, <em>domain</em> y 
  <em>presentation</em>, así como su propio módulo de inyección de dependencias.
</p>

<!-- Requerimientos Técnicos y Prácticos -->
<h2 id="requerimientos">Requerimientos Técnicos y Prácticos</h2>
<ul>
  <li><strong>Pantallas requeridas</strong>: 
    <em>Búsqueda, Resultados y Detalle</em>, cumpliendo con la especificación de la prueba.</li>
  <li><strong>Rotación y mantenimiento de estado</strong>: 
    el patrón MVI con <code>ViewModel</code> garantiza que el estado de la vista se preserve al rotar.</li>
  <li><strong>Manejo de errores</strong>:
    <ul>
      <li>Logs: uso de <em>Crashlytics</em> y SLF4J para reporte de errores.</li>
      <li>Mensajes al usuario: feedback amigable en caso de fallos de red o errores inesperados.</li>
    </ul>
  </li>
  <li><strong>Repositorio público</strong> para la revisión de código.</li>
  <li><strong>Enfoque en calidad</strong>: Arquitectura clara, guías oficiales, 
    <em>tests unitarios</em> y código documentado.</li>
  <li><strong>Compatibilidad</strong>: 
    <em>Mínimo</em> Android 8 (API 27), 
    <em>Target</em> Android 13+ (API 33 o superior), 
    <em>Compile</em> con API 35 en esta versión del proyecto.</li>
  <li><strong>Permisos</strong>: 
    <code>INTERNET</code>, <code>ACCESS_NETWORK_STATE</code>, entre otros necesarios.</li>
</ul>

<!-- Tecnologías y Librerías Utilizadas -->
<h2 id="tecnologias-librerias">Tecnologías y Librerías Utilizadas</h2>

<table>
  <thead>
    <tr>
      <th>Categoría</th>
      <th>Librerías / Frameworks</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Lenguaje</strong></td>
      <td>
        <a href="https://kotlinlang.org" target="_blank">Kotlin (2.0.0)</a>
      </td>
    </tr>
    <tr>
      <td><strong>UI</strong></td>
      <td>
        <a href="https://developer.android.com/jetpack/compose" target="_blank">Jetpack Compose</a>,
        <a href="https://github.com/google/accompanist" target="_blank">Accompanist</a>, 
        Material3, etc.
      </td>
    </tr>
    <tr>
      <td><strong>Arquitectura</strong></td>
      <td>
        MVI, 
        <a href="https://insert-koin.io" target="_blank">Koin</a> (inyección de dependencias)
      </td>
    </tr>
    <tr>
      <td><strong>Networking</strong></td>
      <td>
        <a href="https://ktor.io" target="_blank">Ktor</a> 
        (HTTP Client, logging e intercambio de datos con JSON)
      </td>
    </tr>
    <tr>
      <td><strong>Serialización</strong></td>
      <td>
        <a href="https://github.com/Kotlin/kotlinx.serialization" target="_blank">Kotlinx Serialization</a>
      </td>
    </tr>
    <tr>
      <td><strong>Imágenes</strong></td>
      <td>
        <a href="https://coil-kt.github.io/coil/" target="_blank">Coil</a>
      </td>
    </tr>
    <tr>
      <td><strong>Firebase</strong></td>
      <td>Auth, Crashlytics, BOM</td>
    </tr>
    <tr>
      <td><strong>Testing</strong></td>
      <td>
        <a href="https://junit.org" target="_blank">JUnit</a>, 
        <a href="https://mockk.io" target="_blank">MockK</a>, 
        <a href="https://github.com/Kotlin/kotlinx.coroutines" target="_blank">kotlinx.coroutines.test</a>
      </td>
    </tr>
    <tr>
      <td><strong>Varios</strong></td>
      <td>
        <em>Gradle</em>, <em>Android Gradle Plugin</em>, 
        <em>SLF4J</em> para logging en tests, 
        <em>accompanist-pager</em> para slides y carouseles, etc.
      </td>
    </tr>
    <tr>
      <td><strong>Estilo de Código</strong></td>
      <td>
        <strong>Ktlint</strong> (integrado en el CI con GitHub Actions)
      </td>
    </tr>
  </tbody>
</table>

<!-- Ktlint -->
<h2 id="ktlint">Ktlint</h2>
<p>
  Se ha implementado <strong>Ktlint</strong> para asegurar un código limpio, consistente y conforme a las 
  buenas prácticas. La verificación se ejecuta automáticamente durante el proceso de build a través de GitHub Actions.
</p>
<pre>
# Configuración de Ktlint (ejemplo de .editorconfig)
root = true

[*]
charset = utf-8
end_of_line = lf
indent_size = 4
indent_style = space
insert_final_newline = false
max_line_length = 160
...
</pre>
<p>
  <strong>Resultado de Ktlint:</strong>
</p>
<div style="display: flex; justify-content: center;">
  <img src="https://github.com/user-attachments/assets/9e486d17-7876-4341-a07f-210c337f03f6" alt="Resultado Ktlint" style="width:100%;" />
</div>

<!-- Tests Instrumentados -->
<h2 id="tests-instrumentados">Tests Instrumentados</h2>
<p>
  Se han añadido <strong>tests instrumentados</strong> para validar la funcionalidad de la interfaz de usuario en 
  dispositivos/emuladores reales. Estas pruebas permiten garantizar que la app funcione correctamente en condiciones 
  de uso reales.
</p>
<p>
  <strong>Ejemplo de Test Instrumentado:</strong>
</p>
<div style="display: flex; justify-content: center;">
  <img src="https://github.com/user-attachments/assets/35e329d4-b5ee-46a0-8390-7b4a355d2767" alt="Ejemplo de Test Instrumentado" style="width:80%;" />
</div>
<p>
  <strong>Resultado de Test Instrumentado:</strong>
</p>
<div style="display: flex; justify-content: center;">
  <img src="https://github.com/user-attachments/assets/04e90bd5-2b1b-43c3-a9d3-8f2444f742db" alt="Resultado de Test Instrumentado" style="width:100%;" />
</div>

<!-- Integración con GitHub Actions -->
<h2 id="github-actions">Integración con GitHub Actions</h2>
<p>
  Este proyecto utiliza <strong>GitHub Actions</strong> para automatizar procesos críticos que aseguran la integridad, calidad y estabilidad del código. Cada acción se encarga de ejecutar una serie de tareas que ayudan a detectar errores de manera temprana, garantizar un formato consistente y automatizar el despliegue de la aplicación.
</p>
<p>
  Es importante destacar que <strong>todos los pull requests quedan bloqueados hasta que se aprueban todas las pruebas</strong>, evitando la integración de código defectuoso en la rama principal.
</p>
<div style="display: flex; justify-content: center;">
  <img src="https://github.com/user-attachments/assets/39e9d906-f4c1-4d46-8b4f-f1f42ec41a53" alt="Resultado de GitHub Actions" style="width:80%;" />
</div>
<h3>Acciones Configuradas</h3>
<ul>
  <li>
    <strong>Validaciones PR</strong>: 
    <ul>
      <li>
        <em>Cuándo se activa:</em> En cada pull request dirigido a la rama <code>master</code>.
      </li>
      <li>
        <em>Qué hace:</em> Ejecuta pruebas unitarias y tests instrumentados para validar que los cambios propuestos no rompan la funcionalidad existente. Incluye la configuración del JDK, ejecución de <code>./gradlew test</code> y la inicialización de un emulador Android para los tests instrumentados.
      </li>
      <li>
        <em>Importancia:</em> Garantiza que cada contribución pase por un riguroso proceso de validación, manteniendo un alto estándar de calidad en el código y evitando la integración de cambios defectuosos.
      </li>
    </ul>
  </li>
  <li>
    <strong>Push Build</strong>: 
    <ul>
      <li>
        <em>Cuándo se activa:</em> En cada push a cualquier rama excepto <code>master</code>.
      </li>
      <li>
        <em>Qué hace:</em> Compila la aplicación en modo Debug y ejecuta la verificación del formato del código mediante <strong>Ktlint</strong> (a través de <code>./gradlew ktlintCheck</code>).
      </li>
      <li>
        <em>Importancia:</em> Permite detectar de forma temprana problemas de compilación y de estilo en el código, facilitando la corrección antes de que se integren en la rama principal.
      </li>
    </ul>
  </li>
  <li>
    <strong>Build y Despliegue en Master</strong>: 
    <ul>
      <li>
        <em>Cuándo se activa:</em> Al hacer push directamente a la rama <code>master</code>.
      </li>
      <li>
        <em>Qué hace:</em> Ejecuta una serie de tareas que incluyen:
        <ul>
          <li>Compilación de la aplicación en modo Debug y Release.</li>
          <li>Ejecución de las validaciones de Ktlint y pruebas unitarias.</li>
          <li>Gestión automatizada de releases en GitHub: se borra cualquier release existente etiquetado como "latest" y se crea uno nuevo con el APK más reciente.</li>
        </ul>
      </li>
      <li>
        <em>Importancia:</em> Asegura que el código en la rama principal esté siempre en un estado desplegable y que los releases se actualicen automáticamente con la última versión estable del APK.
      </li>
    </ul>
  </li>
</ul>

<h3>Extracto de la Configuración de GitHub Actions</h3>
<pre>
name: Validaciones PR

on:
    pull_request:
        branches: [ master ]

jobs:
    pr_checks:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout del código del PR
              uses: actions/checkout@v3

            - name: Configurar JDK 17
              uses: actions/setup-java@v3
              with:
                  distribution: 'temurin'
                  java-version: '17'

            - name: Dar permisos de ejecución al Gradle Wrapper
              run: chmod +x ./gradlew

            - name: Ejecutar tests unitarios (u otras validaciones)
              run: ./gradlew test

            # Crear directorio para AVD (en caso de que no exista)
            - name: Crear directorio para AVD
              run: mkdir -p ~/.android/avd

            - name: Configurar emulador Android y ejecutar tests instrumentados
              uses: ReactiveCircus/android-emulator-runner@v2.18.0
              with:
                  api-level: 33
                  target: google_apis
                  arch: x86_64
                  boot-timeout: 300
                  script: ./gradlew connectedCheck
</pre>

<pre>
name: Push Build

on:
    push:
        branches-ignore:
            - master

jobs:
    build:
        runs-on: ubuntu-latest

        steps:
            - name: Checkout del código
              uses: actions/checkout@v3

            - name: Configurar JDK 17
              uses: actions/setup-java@v3
              with:
                  distribution: 'temurin'
                  java-version: '17'

            - name: Dar permisos de ejecución al Gradle Wrapper
              run: chmod +x ./gradlew

            - name: Ejecutar Ktlint y compilar la app (Debug)
              run: ./gradlew ktlintCheck assembleDebug
</pre>

<pre>
name: Build y Despliegue en Master

on:
    push:
        branches:
            - master

permissions:
    contents: write

jobs:
    build_release:
        runs-on: ubuntu-latest

        steps:
            - name: Checkout del código de master
              uses: actions/checkout@v3

            - name: Configurar JDK 17
              uses: actions/setup-java@v3
              with:
                  distribution: 'temurin'
                  java-version: '17'

            - name: Dar permisos de ejecución al Gradle Wrapper
              run: chmod +x ./gradlew

            - name: Ejecutar Ktlint, compilar la app (Debug y Release) y tests unitarios
              run: |
                  ./gradlew ktlintCheck assembleDebug test assembleRelease

            - name: Instalar GitHub CLI
              run: |
                  sudo apt update
                  sudo apt install gh -y

            - name: Borrar Release existente (si existe) con tag "latest"
              env:
                  GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
              run: |
                  if gh release view latest > /dev/null 2>&1; then
                    echo "Release 'latest' encontrado, borrándolo..."
                    gh release delete latest --yes
                  else
                    echo "No existe release 'latest'."
                  fi

            - name: Crear Release con tag "latest"
              env:
                  GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
              run: |
                  gh release create latest -t "Latest APK" -n "Release automatizada con el APK más reciente"

            - name: Subir APK al Release
              env:
                  GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
              run: |
                  gh release upload latest app/build/outputs/apk/release/app-release.apk --clobber
</pre>

<p>
  Con esta configuración, se garantiza que solo se integren cambios que cumplan con todas las pruebas y validaciones necesarias, manteniendo así un estándar alto de calidad en el código del proyecto.
</p>

<!-- Firebase Crashlytics -->
<h2 id="firebase-crashlytics">Firebase Crashlytics</h2>
<p>
  Se ha integrado <strong>Firebase Crashlytics</strong> para el monitoreo en tiempo real de fallos y errores en producción. 
  Esta herramienta permite identificar problemas en la app de manera rápida y efectiva, ayudando a mantener una alta calidad de 
  la experiencia de usuario.
</p>

<p>
  <strong>Captura de Firebase Crashlytics:</strong>
</p>
<div style="display: flex; justify-content: center;">
  <img src="https://github.com/user-attachments/assets/b384f8ab-ef39-44ad-9454-ec9f18b9732b" alt="Firebase Crashlytics" style="width:100%;" />
</div>

<!-- Pruebas Unitarias -->
<h2 id="pruebas-unitarias">Pruebas Unitarias</h2>
<p>
  Para asegurar la calidad del proyecto, se implementaron pruebas <strong>unitarias</strong> en las capas críticas:
</p>
<ul>
  <li><em>ViewModels</em>: validación de flujos MVI (intents, estados, etc.).</li>
  <li><em>UseCases</em>: lógica de negocio (cálculos, validaciones, etc.).</li>
  <li><em>Repositorios</em>: correcto manejo y mapeo de datos (API de Mercado Libre, Firebase, etc.).</li>
</ul>
<p>
  Estructura de los tests:
</p>
<pre>
com/
└── luis/
    └── dev/
        └── meliapp/
            ├── core/
            │   └── components/
            │       └── searchTopAppBar/
            │           └── SearchBarViewModelTest.kt
            ├── features/
            │   ├── authentication/
            │   │   ├── data/
            │   │   │   └── repository/
            │   │   │       └── AuthRepositoryImplTest.kt
            │   │   ├── domain/
            │   │   │   └── usecases/
            │   │   │       ├── LoginUseCaseTest.kt
            │   │   │       ├── RegisterUseCaseTest.kt
            │   │   │       ├── ResetPasswordUseCaseTest.kt
            │   │   └── presentation/
            │   │       ├── login/
            │   │       │   └── LoginViewModelTest.kt
            │   │       ├── register/
            │   │       │   └── RegisterViewModelTest.kt
            │   │       └── reset/
            │   │           └── ResetPasswordViewModelTest.kt
            │   ├── details/
            │   │   ├── data/
            │   │   │   └── repository/
            │   │   │       └── DetailsRepositoryImplTest.kt
            │   │   ├── domain/
            │   │   │   └── usecases/
            │   │   │       └── GetItemDetailUseCaseImplTest.kt
            │   │   └── presentation/
            │   │       └── DetailsViewModelTest.kt
            │   └── results/
            │       ├── data/
            │       │   └── repository/
            │       │       └── ResultsRepositoryImplTest.kt
            │       ├── domain/
            │       │   └── usecases/
            │       │       └── GetResultsUseCaseImplTest.kt
            │       └── presentation/
            │           └── ResultsViewModelTest.kt
            └── ExampleUnitTest.kt
</pre>
<p>
  Se emplean <code>MockK</code> y <code>kotlinx.coroutines.test</code> para aislar las dependencias y verificar comportamientos específicos.
</p>
<p>
  <strong>Resultado de Test Unitarios:</strong>
</p>
<div style="display: flex; justify-content: center;">
  <img src="https://github.com/user-attachments/assets/d08574ba-33c3-462e-9bcb-f756e445f9dd" alt="Resultado de Test Unitarios" style="width:100%;" />
</div>

<!-- Cómo Ejecutar el Proyecto -->
<h2 id="ejecutar">Cómo Ejecutar el Proyecto</h2>
<ol>
  <li>
    <strong>Clonar el repositorio</strong>:
    <pre><code>git clone https://github.com/LuisDev2576/meliapp.git
cd meliapp</code></pre>
  </li>
  <li>
    <strong>Abrir en Android Studio</strong>:
    <ul>
      <li>Se requiere versión Giraffe (o posterior), y SDK de Android 13 (API 33) en adelante.</li>
    </ul>
  </li>
  <li>
    <strong>Sincronizar dependencias</strong>:
    <ul>
      <li>Android Studio descargará los Gradle wrappers y librerías.</li>
    </ul>
  </li>
  <li>
    <strong>Ejecutar el proyecto</strong>:
    <ul>
      <li>Selecciona “Run 'app'” en la barra de herramientas y elige un dispositivo/emulador.</li>
    </ul>
  </li>
  <li>
    <strong>Ejecutar pruebas unitarias</strong> (opcional):
    <pre><code>./gradlew test</code></pre>
    <p>Si alguna prueba de coroutines genera resultados inconsistentes (falsos positivos), 
    vuelve a ejecutar o haz un rebuild del proyecto.</p>
  </li>
</ol>

<!-- Download -->
<h2 id="download">Download</h2>
<p>
  Puedes descargar la aplicación mediante el archivo APK proporcionado.
</p>
<div style="display: flex; gap: 20px; flex-wrap: wrap; justify-content: center;">
  <a href="https://github.com/LuisDev2576/MeliApp/releases/tag/latest" target="_blank">
    <img src="https://github.com/user-attachments/assets/6246d1a5-2a8a-44d4-8eda-1c98d8f2c035" alt="Descargar APK" width="200px"/>
  </a>
</div>

<hr/>

<!-- Capturas de Pantalla -->
<h2 id="capturas">Capturas de Pantalla</h2>
<p>Añade tus capturas de pantalla relevantes aquí:</p>
<ol>
  <li>
    <strong>Pantallas de Autenticación</strong><br/>
    <div style="display: flex; gap: 10px; flex-wrap: wrap;">
      <img src="https://github.com/user-attachments/assets/f49ba6b1-3912-468a-941f-e551f21fb8f9" alt="Pantalla de Inicio" style="width: 30%;"/>
      <img src="https://github.com/user-attachments/assets/a96fa2af-e5bc-4f0d-ad3c-ae8798b53967" alt="Pantalla de Registro" style="width: 30%;"/>
      <img src="https://github.com/user-attachments/assets/d1129775-d57b-4b68-a809-4851bc09ea45" alt="Pantalla de Restablecer Contraseña" style="width: 30%;"/>
    </div>
  </li>
  <li>
    <strong>Pantalla de Home</strong><br/>
    <div style="display: flex; gap: 10px; flex-wrap: wrap;">
      <img src="https://github.com/user-attachments/assets/97b25d11-fe3a-4cfb-a9ae-cd502e4d8dda" alt="Pantalla de Resultados" style="width: 45%;"/>
    </div>
  </li>
  <li>
    <strong>Pantalla de Resultados</strong><br/>
    <div style="display: flex; gap: 10px; flex-wrap: wrap;">
      <img src="https://github.com/user-attachments/assets/e60e06b6-f320-45c8-a110-95ad58d597c3" alt="Pantalla de Resultados" style="width: 45%;"/>
      <img src="https://github.com/user-attachments/assets/08394e89-d26c-46e7-8f5f-ffcab1f4e8ef" alt="Pantalla de Resultados Rotada" style="width: 45%;"/>
    </div>
  </li>
  <li>
    <strong>Pantalla de Detalle</strong><br/>
    <div style="display: flex; gap: 10px; flex-wrap: wrap;">
      <img src="https://github.com/user-attachments/assets/e5f146e2-7692-4554-ad46-635af316be21" alt="Pantalla de Detalle" style="width: 45%;"/>
      <img src="https://github.com/user-attachments/assets/7802a4a5-011d-49ab-bc62-b122f545260c" alt="Pantalla de Detalle Rotada" style="width: 45%;"/>
    </div>
  </li>
</ol>

<!-- Contribuciones -->
<h2 id="contribuciones">Contribuciones</h2>
<p>
  ¡Todas las aportaciones son apreciadas! Si tienes sugerencias, ideas o encuentras errores, no dudes en abrir un 
  <a href="LINK_A_ISSUES" target="_blank">issue</a> con tus sugerencias, ideas o reporte de bugs. 
  También puedes contribuir directamente creando un <a href="LINK_A_PULL_REQUEST" target="_blank">pull request</a> con tus 
  cambios propuestos.
</p>

<!-- Licencia -->
<h2 id="licencia">Licencia</h2>
<p>
  Este proyecto se distribuye bajo los términos de la 
  <strong>Apache License 2.0</strong>. Para más detalles, revisa el archivo 
  <code>LICENSE</code> en este repositorio.
</p>
<pre>
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/LICENSE-2.0
</pre>
<p>
  Salvo que la ley lo exija o se haya acordado por escrito, este software se distribuye "TAL CUAL", SIN NINGUNA GARANTÍA NI CONDICIONES DE NINGÚN TIPO, ya sean explícitas o implícitas. Consulta la licencia para conocer más sobre los permisos y las limitaciones aplicables.
</p>

</body>
