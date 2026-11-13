Here is a professional, detailed `README.md` template tailored for your **REST Assured E2E API Automation Framework** repository. You can create a file named `README.md` in the root of your IntelliJ project and paste this content directly:

```markdown
# REST Assured E2E API Automation Framework

A robust, production-grade automated API testing framework built using **Java**, **REST Assured**, **TestNG**, and **Maven**. This framework implements modular design patterns (such as `RequestSpecBuilder` and strongly-typed POJOs) to automate complete end-to-end (E2E) e-commerce lifecycles.

---

## 🚀 Tech Stack & Dependencies

* **Language:** Java (JDK 11+)
* **Test Runner:** TestNG
* **Build Tool:** Maven
* **API Testing Library:** REST Assured (v5.5.0)
* **Serialization/Deserialization:** Jackson / Gson
* **JSON Parsing:** Groovy JSONPath

---

## 📂 Project Structure

```text
RestAssured Framework/
├── src/
│   ├── main/java/
│   │   └── POJO/                   # Strongly-typed Request & Response POJO classes
│   │       ├── LoginRequest.java
│   │       ├── LoginResponse.java
│   │       ├── OrderDetail.java
│   │       └── Orders.java
│   └── test/java/
│       └── Tests/                  # Test suite classes
│           └── EcommerceAPITest.java # Complete E2E test lifecycle
├── pom.xml                         # Maven dependencies and build plugins
└── testng.xml                      # TestNG suite configuration

```

---

## 🔄 Automated E2E API Workflow

The framework automates the full e-commerce backend workflow in a single chained test sequence:

1. **Authentication API (`POST /api/ecom/auth/login`)**
* Authenticates user credentials and extracts the session `token` and `userId`.


2. **Add Product API (`POST /api/ecom/product/add-product`)**
* Sends form parameters along with a multipart file attachment (`productImage`) using the extracted authorization token.
* Extracts the generated `productId`.


3. **Create Order API (`POST /api/ecom/order/create-order`)**
* Places an order using the newly created `productId` and extracts the generated `orderId`.


4. **Cleanup APIs (`DELETE`)**
* Deletes the product and order to maintain clean test data hygiene.



---

## ⚙️ Prerequisites & Local Setup

1. **Clone the Repository:**
```bash
git clone [https://github.com/](https://github.com/)<your-username>/RestAssured-Framework.git

```


2. **Open in IDE:**
* Open **IntelliJ IDEA**, select **File $\rightarrow$ Open**, and choose the project root folder.


3. **Import Maven Dependencies:**
* Ensure Maven automatically imports dependencies from `pom.xml`, or right-click `pom.xml` $\rightarrow$ **Maven $\rightarrow$ Reload Project**.



---

## 🏃‍♂️ How to Run Tests

### Option 1: From IntelliJ IDEA

* Navigate to `src/test/java/Tests/EcommerceAPITest.java`.
* Click the green **Play** icon next to the class or test method to run via TestNG.

### Option 2: Using Maven CLI

Run the full test suite from your terminal:

```bash
mvn clean test

```

---

## 🛠️ Core Architectural Highlights

* **Reusability:** Encapsulates common headers, content types, and base URIs using `RequestSpecBuilder`, adhering to the **DRY (Don't Repeat Yourself)** principle.
* **Type-Safety:** Utilizes Java POJOs with getters and setters for clean request payload serialization and response mapping.
* **Dynamic Chaining:** Passes runtime-extracted parameters (`token`, `userId`, `productId`, `orderId`) seamlessly across consecutive API calls.

```

```
