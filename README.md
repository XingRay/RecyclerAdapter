# RecyclerAdapter
Adapter for RecyclerView

## How to 
To get a Git project into your build:

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

``` groovy

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```

### Step 2. Add the dependency

[![2.0.1](https://jitpack.io/v/XingRay/RecyclerAdapter.svg)](https://jitpack.io/#XingRay/RecyclerAdapter)

``` groovy

	dependencies {
	        implementation 'com.github.XingRay:RecyclerAdapter:2.0.1'
	}

```

### step 3. code

#### kotlin 

```kotlin

// init adapter for single layout
mAdapter = RecyclerAdapter(applicationContext)
		.typeSupport(TestData::class.java)
		.layoutViewSupport(R.layout.item_recycler_view_test_list)
		.viewHolder(TestViewHolder::class.java)
		.itemClickListener { _, position, t ->
			showToast("$position ${t?.name} clicked")
		}.registerView().registerType()

rvList.adapter = mAdapter

// loadData
mAdapter.update(mRepository.loadData())

```

```kotlin

// init adapter for multi-Data and multi-Layout
mAdapter = RecyclerAdapter(applicationContext)
		.typeSupport(TestData::class.java)
		.viewSupport(R.layout.item_recycler_view_test_list, TestViewHolder::class.java, 0) { _, position, t ->
			showToast("$position ${t.name} clicked layout0")
		}.viewSupport(R.layout.item_recycler_view_test_list1, TestViewHolder1::class.java, 1) { _, position, t ->
			showToast("$position ${t.name} clicked layout1")
		}.viewTypeMapper { _, position ->
			position % 2
		}.registerType()
		.typeSupport(TestData1::class.java)
		.viewSupport(R.layout.item_recycler_view_test1_list, TestData1ViewHolder::class.java, 2) { _, position, t ->
			showToast("$position ${t.name} ${t.size} clicked TestData1")
		}.registerType()

rvList.adapter = mAdapter

// load multi-Data
val list = mutableListOf<Any>()
list.addAll(mRepository.loadData())
list.addAll(mRepository.loadData1())
list.shuffle()

mAdapter.addAll(list)

```
#### java

```java

// init adapter
RecyclerView rvList = findViewById(R.id.rv_list);

rvList.setLayoutManager(new LinearLayoutManager(mContext));

mAdapter = new RecyclerAdapter(getApplicationContext())
	.typeSupport(TestData.class)
	.layoutViewSupport(R.layout.item_recycler_view_test_list)
	.viewHolder(TestViewHolder.class)
	.itemClickListener(new ItemClickListener<TestData>() {
		@Override
		public void onItemClick(@NotNull ViewGroup parent, int position, @org.jetbrains.annotations.Nullable TestData testData) {
			UiUtil.showToast(JavaTestActivity.this, position + "" + testData.getName() + " clicked");
		}
	}).registerView().registerType();

rvList.setAdapter(mAdapter);

// load data
mAdapter.update(mRepository.loadData());

```

more usage see sample
