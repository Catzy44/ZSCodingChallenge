# Zadanie rekrutacyjne dla Z...S Przemysław Witczak

Mam nadzieję, że nie zrobiłem całości za bardzo "Enterprise" i że nie zostanie to uznane za "Over-engineering", ale po rozmowie rekrutacyjnej uznałem że właśnie tak powinienem podejść do problemu.

**NIE wykorzystałem frameworka Spring**, uznałem to za zbyteczne i nie było żadnej mowy o tym w instrukcji.

Jeżeli życzą sobie Państwo, mogę szybko przerobić cały projekt tak, aby był zbudowany na Springu.

Poświęcony czas to około 6 godzin, w kilku blokach.

# Architektura programu

Projekt opiera się na buildsystemie **Gradlew**.  
Starałem się korzystać z **nowoczesnych API Javy**, w kilku miejscach wykorzystałem **Project Lombok**  
Jako bibliotekę JSON wybrałem **Gson**, a do testów automatycznych **JUnit5**  
Wykorzystałem również **Apache Commons**'y (**NET**), **Apache FTP** oraz **slf4j**

**DataProvidery**, **Parsery**, **Sortery** oraz **Formattery** to elementy **abstrakcyjne** - interfejsy.  
Dla każdego z tych interfejsów istnieje **Registry** zawierający wszystkie jego implementacje.  
**Podczas dodawania nowych implementacji należy uzupełnić Registry**.  

**Umożliwia to** szybkie przełączanie implementacji za pomocą konfiguracji, oraz zbiorowe **testowanie wszystkich implementacji naraz**.

Konfiguracja aplikacji - z których implementacji ma akurat korzystać jest "IN-CODE":
* me.kotsu.config.prod.AppConfigurationProd.java
	
Konfiguracja również jest abstrakcyjna, istnieje też druga implementacja konfiguracji, do testów automatycznych.  


Główny przepływ danych następuje w **Serwisie**:
* me.kotsu.MainService.java

**Punkt wejściowy** aplikacji znajduje się w:
* me.kotsu.Main.java

# Zachowanie programu
(Wrzuciłem to też w JavaDoc)

Zachowanie DataProviderów:  
a) Plik istnieje - zwraca plik w Optional<>  
b) Plik nie istnieje - zwraca pusty Optional<>  
c) Błąd IO, błąd serwera, błąd klienta - wyjątek FetchException zawierający orginalny wyjątek  

Zachowanie Parserów:  
a) Parsowanie udane - zwraca zdekodowane obiekty w Optional<>  
b) Brak danych do parsowania - pusty Optional<>  
b) Parsowanie nieudane - zwraca wyjątek ParsingException j.w.  

Zachowanie Sorterów:  
a) Sortowanie udane - zwraca posortowany Optional<>  
b) Piorun kulisty wleciał do biura, trafił w komputer i sortowanie siadło - zwraca SortingException j.w.  
(czyli każda nieprzewidziana sytuacja skutkuje SortingException)

Zachowanie Formatterów:  
a) Formatowanie udane - zwraca string  
b) null na wejściu - zwraca wyjątek

Mierzenie czasu zostało całkowicie oddzielone od logiki i miało mieć miejsce za pomocą **Aspektów**/**Anotacji**.  
Ale bardzo mi się stawiał **AspectJ** bez **Springa** na nowym **JVM**, więc zrezygnowałem z tego i postawiłem na prosty **Dekorator**.  
**Dekorator** ten implementuje ten sam interfejs i jest wstrzykiwany w konfiguracji.  
Można go bardzo łatwo i szybko wyłączyć lub włączyć.

# Testy

Testy automatyczne sprawdzają zachowanie wszystkich implementacji danego interfejsu, zbierając je z Registry.  
Testowane są Sortery, Parsery,  DataProvidery I Formattery.  
Z uwagi na budowę projektu nie są testowane poszczególne implementacje.  
Stawiane są proste serwery HTTP i FTP do testowania tych implementacji DataProviderów.

W instrukcji napisano, aby pobrać gotowe implementacje algorytmów sortujących.  
Postanowiłem również wygenerować do nich półautomatycznie testy za pomocą LLM, w celu przyspieszenia pracy i zachowania spójności stylu testów.  
To jedyne miejsce w aplikacji w którym wykorzystałem LLM. Reszta została napisana ręcznie, bez wspomagaczy.
